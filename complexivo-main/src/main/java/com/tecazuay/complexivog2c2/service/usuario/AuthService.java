package com.tecazuay.complexivog2c2.service.usuario;

import com.tecazuay.complexivog2c2.dto.carreraAlumano.CarreraAlumnoResponse;
import com.tecazuay.complexivog2c2.dto.usuarios.RegisterRequest;
import com.tecazuay.complexivog2c2.dto.usuarios.UserEmailResponse;
import com.tecazuay.complexivog2c2.dto.usuarios.UserRequest;
import com.tecazuay.complexivog2c2.dto.usuarios.UserResponse;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.alumnos.Alumnos;
import com.tecazuay.complexivog2c2.model.Primary.autoridades.Autoridades;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorCarrera;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorAcademicoDelegados;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmpresarial;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.roles.Roles;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import com.tecazuay.complexivog2c2.repository.Primary.alumnos.AlumnosRepository;
import com.tecazuay.complexivog2c2.repository.Primary.autoridades.AutoridadesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera.CoordinadorRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpresarialProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.roles.RolesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.alumnos.VAlumnosRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.docentes.DocentesAllRepository;
import com.tecazuay.complexivog2c2.repository.Secondary.personas.PersonasRepository;
import com.tecazuay.complexivog2c2.security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DocentesAllRepository docenteRepository;
    @Autowired
    private PersonasRepository personasRepository;
    @Autowired
    private AutoridadesRepository autoridadesRepository;
    @Autowired
    private CoordinadorRepository coordinadorRepository;
    @Autowired
    private CoordinadorVinculacionRepository coordinadorVinculacionRepository;
    @Autowired
    private TutorEmpresarialProyectoRepository tutorEmpresarialProyectoRepository;
    @Autowired
    private TutorAcademicoRepository tutorAcademicoRepository;
    @Autowired
    private ResponsablePPPRepository responsablePPPRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AlumnosRepository alumnosRepository;
    @Autowired
    private VAlumnosRepository vAlumnosRepository;

    @Transactional
    public UserResponse login(UserRequest userRequest) throws Exception {
        Optional<Usuario> optional = usuarioRepository.findByEmail(userRequest.getEmail());
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setUrlFoto(userRequest.getUrlFoto());
            usuario.setRoles(getRol(optional.get().getCedula()));
            usuarioRepository.save(usuario);
            if (usuario != null) {
                switch (getRol(usuario.getCedula()).getCodigo()) {
                    case "AUT":
                        Autoridades aut = autoridadesRepository.findByCedula(usuario.getCedula()).orElse(new Autoridades());
                        aut.setUsuario(usuario);
                        autoridadesRepository.save(aut);
                        break;
                    case "CC":
                        CoordinadorCarrera cc = coordinadorRepository.findByCedula(usuario.getCedula()).orElse(new CoordinadorCarrera());
                        cc.setUsuario(usuario);
                        coordinadorRepository.save(cc);
                        break;
                    case "CV":
                        CoordinadorVinculacion cv = coordinadorVinculacionRepository.findByCedula(usuario.getCedula()).orElse(new CoordinadorVinculacion());
                        cv.setUsuario(usuario);
                        coordinadorVinculacionRepository.save(cv);
                        break;
                    case "TA":
                        TutorAcademicoDelegados da = tutorAcademicoRepository.findByCedula(usuario.getCedula()).orElse(new TutorAcademicoDelegados());
                        da.setUsuario(usuario);
                        tutorAcademicoRepository.save(da);
                        break;
                    case "TE":
                        TutorEmpresarial dp = tutorEmpresarialProyectoRepository.findByCedula(usuario.getCedula()).orElse(new TutorEmpresarial());
                        dp.setUsuario(usuario);
                        tutorEmpresarialProyectoRepository.save(dp);
                        break;
                    case "RPPP":
                        ResponsablePPP rp = responsablePPPRepository.findByCedulaAndEstado(usuario.getCedula(),true).orElse(new ResponsablePPP());
                        rp.setUsuario(usuario);
                        responsablePPPRepository.save(rp);
                        break;
                }
                return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUrlFoto(), getNombre(usuario.getCedula()), usuario.getCedula(), usuario.getRoles().getCodigo(), generateTokenLogin(userRequest));
            } else {
                throw new BadRequestException("No se pudo guardar el usuario");
            }
        }else{
            log.info("EMAIL NO EXISTE");
        }
        log.info("AFUERA LOGIN");
        return null;
    }


    @Transactional
    public UserResponse signup(RegisterRequest registerRequest) throws Exception {
        Usuario newUser = new Usuario();
        newUser.setCedula(registerRequest.getCedula());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUrlFoto(registerRequest.getUrlFoto());
        newUser.setRoles(getRol(registerRequest.getCedula()));
        if (!getUsuarioPPP(registerRequest.getCedula())) {
            if (getPersonaFenix(registerRequest.getCedula())) {
                Usuario usuario = usuarioRepository.save(newUser);
                if (usuario != null) {
                    switch (getRol(usuario.getCedula()).getCodigo()) {
                        case "AUT":
                            Autoridades aut = autoridadesRepository.findByCedula(usuario.getCedula()).orElse(new Autoridades());
                            aut.setUsuario(usuario);
                            aut.setCedula(usuario.getCedula());
                            autoridadesRepository.save(aut);
                            break;
                        case "CC":
                            CoordinadorCarrera cc = coordinadorRepository.findByCedula(usuario.getCedula()).orElse(new CoordinadorCarrera());
                            cc.setUsuario(usuario);
                            cc.setCedula(usuario.getCedula());
                            coordinadorRepository.save(cc);
                            break;
                        case "CV":
                            CoordinadorVinculacion cv = coordinadorVinculacionRepository.findByCedula(usuario.getCedula()).orElse(new CoordinadorVinculacion());
                            cv.setUsuario(usuario);
                            cv.setCedula(usuario.getCedula());
                            coordinadorVinculacionRepository.save(cv);
                            break;
                        case "TA":
                            TutorAcademicoDelegados da = tutorAcademicoRepository.findByCedula(usuario.getCedula()).orElse(new TutorAcademicoDelegados());
                            da.setUsuario(usuario);
                            da.setCedula(usuario.getCedula());
                            tutorAcademicoRepository.save(da);
                            break;
                        case "TE":
                            TutorEmpresarial dp = tutorEmpresarialProyectoRepository.findByCedula(usuario.getCedula()).orElse(new TutorEmpresarial());
                            dp.setUsuario(usuario);
                            dp.setCedula(usuario.getCedula());
                            tutorEmpresarialProyectoRepository.save(dp);
                            break;
                        case "RPPP":
                            ResponsablePPP rp = responsablePPPRepository.findByCedulaAndEstado(usuario.getCedula(),true).orElse(new ResponsablePPP());
                            rp.setUsuario(usuario);
                            rp.setCedula(usuario.getCedula());
                            responsablePPPRepository.save(rp);
                            break;
                        case "EST":
                            Alumnos a = new Alumnos();
                            a.setCedula(registerRequest.getCedula());
                            a.setUsuario(usuario);
                            alumnosRepository.save(a);
                            break;

                    }
                    return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUrlFoto(), (getNombre(usuario.getCedula())), usuario.getCedula(), usuario.getRoles().getCodigo(), generateTokenSignUp(registerRequest));

                } else {
                    log.error("No se puedo guardar el usuario con cédula: {} e email: {}", registerRequest.getCedula(), registerRequest.getEmail());
                    throw new BadRequestException("No se pudo guardar el usuario");
                }
            } else {
                throw new ResponseNotFoundException("Usuario", "cedula", registerRequest.getCedula());
            }
        } else {
            log.error("La cédula ya está registrada: {}", registerRequest.getCedula());
            throw new BadRequestException("La cedula ingresada, ya esta registrada, si la cedula le pertenece contactenos a");
        }
    }

    /**
     * Obtiene el usuario de acuerdo al campo email
     * @param email
     * @return UserResponse
     */
    public UserResponse getUser(String email) {
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUrlFoto(), (getNombre(usuario.getCedula())), usuario.getCedula(), usuario.getRoles().getCodigo());
        }
        throw new ResponseNotFoundException("Usuario", "email", email);
    }

    /**
     * Al momento de registrarse se verifica el rol, y si existe en un tabla de bd_vinculacion
     * de acuerdo a una previa asignación, caso contrario, se obtiene el rol según Fénix
     * @param cedula
     * @return Rol
     */
    private Roles getRol(String cedula) {
        if (autoridadesRepository.existsByCedula(cedula)) {
            return rolesRepository.findByCodigo("AUT").get();
        }  if (coordinadorRepository.existsByCedula(cedula)) {
            return rolesRepository.findByCodigo("CC").get();
        }
        Optional<CoordinadorVinculacion> optional =coordinadorVinculacionRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            if(optional.get().isEstado()){
                return rolesRepository.findByCodigo("CV").get();
            }
        }
        Optional<TutorEmpresarial>director= tutorEmpresarialProyectoRepository.findByCedula(cedula);
        if (director.isPresent()) {
            if(director.get().isEstado()){
                return rolesRepository.findByCodigo("DP").get();
            }

        }
        Optional<TutorAcademicoDelegados>apoyo= tutorAcademicoRepository.findByCedula(cedula);
        if (apoyo.isPresent()) {
            if(apoyo.get().isEstado()){
                return rolesRepository.findByCodigo("DA").get();
            }
        }
        if ( responsablePPPRepository.existsByCedulaAndEstado(cedula,true)) {
            return rolesRepository.findByCodigo("RPPP").get();
        } else {
            Optional<VPersonasppp> vp = personasRepository.findByCedula(cedula);
            if (vp.isPresent()) {
                if (vp.get().getRol_nombre().equals("ALUMNO")) {
                    return rolesRepository.findByCodigo("EST").get();
                }
                if (vp.get().getRol_nombre().equals("DOCENTE")) {
                    return rolesRepository.findByCodigo("DOC").get();
                }
                if (vp.get().getRol_nombre().equals("COORDINADOR")) {
                    return rolesRepository.findByCodigo("CC").get();
                }
            }
        }
        throw new BadRequestException("La cedula ingresada no existe");
    }

    /**
     * Se obtiene el nombre de la persona de acuerdo a la cédula desde Fénix
     * @param cedula
     * @return Nombre
     */
    private String getNombre(String cedula) {
        Optional<VPersonasppp> listaUsers = personasRepository.findByCedula(cedula);
        if (listaUsers.isPresent()) {
            return listaUsers.get().getNombres() + " " + listaUsers.get().getApellidos();
        } else {
            throw new BadRequestException("No existe usuario(Nombre)");
        }

    }

    /**
     * Comprobamos si existe la persona en el Fénix de acuerdo a la cedula
     * @param cedula
     * @return Boolean (true-false)
     */
    private boolean getPersonaFenix(String cedula) {
        return personasRepository.existsByCedula(cedula);
    }

    /**
     * Comprobamos si existe la persona en la tabla usuario de acuerdo a la cedula
     * en la base bd_vinculacion
     * @param cedula
     * @return Boolean (true-false)
     */
    private boolean getUsuarioPPP(String cedula) {
        return usuarioRepository.existsByCedula(cedula);
    }

    /**
     * Asiga un token a un suario de acuerdo al email
     * username=email
     * password=email
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(usuario.get().getEmail(), usuario.get().getEmail(), new ArrayList<>());
    }

    /**
     * Generamos y autenticamos el token para el metodo de Login
     * @param userRequest
     * @return
     * @throws Exception
     */
    public String generateTokenLogin(UserRequest userRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getEmail())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en login de usuario con email: {}", userRequest.getEmail());
            throw new Exception("INAVALID");
        }
        return jwtUtil.generateToken(userRequest.getEmail());
    }

    /**
     * Generamos y autenticamos el token para el metodo de SignUp
     * @param registerRequest
     * @return
     * @throws Exception
     */
    public String generateTokenSignUp(RegisterRequest registerRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getEmail())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en signup de usuario con email: {}", registerRequest.getEmail());
            throw new BadRequestException("INAVALID");
        }
        return jwtUtil.generateToken(registerRequest.getEmail());
    }

    public CarreraAlumnoResponse enviarAlumno(String cedula){
        CarreraAlumnoResponse ca= new CarreraAlumnoResponse();
        vAlumnosRepository.findByCedula(cedula).stream().forEach(alumnos -> {

            ca.setCedula(alumnos.getCedula());
            ca.setCodigoCarrera(alumnos.getCodigoCarrera());

        });
        return ca;
    }

    public UserEmailResponse userByCedula(String cedula) {
        Optional<Usuario> optional = usuarioRepository.findByCedula(cedula);
        if (optional.isPresent()) {

            return new UserEmailResponse(optional.get().getEmail());
        }
        throw new ResponseNotFoundException("Usuario", "cédula", cedula);
    }
}
