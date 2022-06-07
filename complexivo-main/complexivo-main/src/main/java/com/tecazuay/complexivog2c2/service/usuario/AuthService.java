package com.tecazuay.complexivog2c2.service.usuario;

import com.tecazuay.complexivog2c2.dto.carreraAlumano.CarreraAlumnoResponse;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaRequest;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaResponse;
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
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.TutorEmp;
import com.tecazuay.complexivog2c2.model.Primary.desigaciones.ResponsablePPP;
import com.tecazuay.complexivog2c2.model.Primary.empresa.Empresa;
import com.tecazuay.complexivog2c2.model.Primary.roles.Roles;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import com.tecazuay.complexivog2c2.repository.Primary.alumnos.AlumnosRepository;
import com.tecazuay.complexivog2c2.repository.Primary.autoridades.AutoridadesRepository;
import com.tecazuay.complexivog2c2.repository.Primary.coordinadorCarrera.CoordinadorRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorEmpProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.TutorAcademicoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.ResponsablePPPRepository;
import com.tecazuay.complexivog2c2.repository.Primary.empresa.EmpresaRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
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
    private TutorEmpProyectoRepository tutorEmpProyectoRepository;
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


    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;



    @Transactional
    public UserResponse login(UserRequest userRequest) throws Exception {
        Optional<Usuario> optional = usuarioRepository.findByEmail(userRequest.getEmail());
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setUrlFoto(userRequest.getUrlFoto());
            usuario.setRoles(getRol(optional.get().getCedula()));
            usuarioRepository.save(usuario);
            if (usuario != null) {
                try {
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
                            TutorEmp dp = tutorEmpProyectoRepository.findByCedula(usuario.getCedula()).orElse(new TutorEmp());
                            dp.setUsuario(usuario);
                            tutorEmpProyectoRepository.save(dp);
                            break;
                        case "RPPP":
                            ResponsablePPP rp = responsablePPPRepository.findByCedulaAndEstado(usuario.getCedula(), true).orElse(new ResponsablePPP());
                            rp.setUsuario(usuario);
                            responsablePPPRepository.save(rp);
                            break;
                    }
                } catch (Exception e) {
                    log.error("Error login: " + e.getMessage());
                }
                return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUrlFoto(), getNombre(usuario.getCedula()), usuario.getCedula(), usuario.getRoles().getCodigo(), generateTokenLogin(userRequest));
            } else {
                throw new BadRequestException("No se pudo guardar el usuario");
            }
        } else {
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
                    try {
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
                                TutorEmp dp = tutorEmpProyectoRepository.findByCedula(usuario.getCedula()).orElse(new TutorEmp());
                                dp.setUsuario(usuario);
                                dp.setCedula(usuario.getCedula());
                                tutorEmpProyectoRepository.save(dp);
                                break;
                            case "RPPP":
                                ResponsablePPP rp = responsablePPPRepository.findByCedulaAndEstado(usuario.getCedula(), true).orElse(new ResponsablePPP());
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
                    } catch (Exception e) {
                        log.error("Error signup: " + e.getMessage());
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


    public UserResponse getUser(String email) {
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUrlFoto(), (getNombre(usuario.getCedula())), usuario.getCedula(), usuario.getRoles().getCodigo());
        }
        throw new ResponseNotFoundException("Usuario", "email", email);
    }


    private Roles getRol(String cedula) {
        if (autoridadesRepository.existsByCedula(cedula)) {
            return rolesRepository.findByCodigo("AUT").get();
        }
        if (coordinadorRepository.existsByCedula(cedula)) {
            return rolesRepository.findByCodigo("CC").get();
        }
        Optional<CoordinadorVinculacion> optional = coordinadorVinculacionRepository.findByCedula(cedula);
        if (optional.isPresent()) {
            if (optional.get().isEstado()) {
                return rolesRepository.findByCodigo("CV").get();
            }
        }
        Optional<TutorEmp> director = tutorEmpProyectoRepository.findByCedula(cedula);
        if (director.isPresent()) {
            if (director.get().isEstado()) {
                return rolesRepository.findByCodigo("TE").get();
            }

        }
        Optional<TutorAcademicoDelegados> apoyo = tutorAcademicoRepository.findByCedula(cedula);
        if (apoyo.isPresent()) {
            if (apoyo.get().isEstado()) {
                return rolesRepository.findByCodigo("TA").get();
            }
        }
        if (responsablePPPRepository.existsByCedulaAndEstado(cedula, true)) {
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


    private String getNombre(String cedula) {
        Optional<VPersonasppp> listaUsers = personasRepository.findByCedula(cedula);
        if (listaUsers.isPresent()) {
            return listaUsers.get().getNombres() + " " + listaUsers.get().getApellidos();
        } else {
            throw new BadRequestException("No existe usuario(Nombre)");
        }

    }


    private boolean getPersonaFenix(String cedula) {
        return personasRepository.existsByCedula(cedula);
    }


    private boolean getUsuarioPPP(String cedula) {
        return usuarioRepository.existsByCedula(cedula);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        Optional<Empresa> empresa = empresaRepository.findByEmailEmpresa(email);


         if(empresa.isPresent()){
             return new org.springframework.security.core.userdetails.User(empresa.get().getEmailEmpresa(), empresa.get().getEmailEmpresa(), new ArrayList<>());

         }else
        if(usuario.isPresent()){
            return new org.springframework.security.core.userdetails.User(usuario.get().getEmail(), usuario.get().getEmail(), new ArrayList<>());
        }
         else {
             return new org.springframework.security.core.userdetails.User(usuario.get().getEmail(), usuario.get().getEmail(), new ArrayList<>());
         }

    }




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

    public CarreraAlumnoResponse enviarAlumno(String cedula) {
        CarreraAlumnoResponse ca = new CarreraAlumnoResponse();
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










    private boolean getEmpresa(String emailEmpresa) {

        return  empresaRepository.existsByEmailEmpresa(emailEmpresa);
    }


    //INICIAR SESIÓN Sin try catch

//    @Transactional
//    public EmpresaResponse login2(EmpresaRequest empresaRequest) throws Exception {
//        Optional<Empresa> optional = empresaRepository.findByEmailEmpresa(empresaRequest.getEmailEmpresa());
//        if (optional.isPresent() ) {
//            try {
//
//                Empresa empresa = optional.get();
//                if(empresa!=null){
//                    try {
//                        if(empresaRequest.getClave().equals(empresa.getClave())){
//                            return  new EmpresaResponse(empresa.getId(), empresa.getEmailEmpresa(),empresa.getClave(),generateTokenLogin2(empresaRequest));
//                        }else{
//                            throw new Exception("La contraseña es incorrecta");
//                        }
//                    } catch (Exception e) {
//                        log.error("Error login: " + e.getMessage());
//                    }
//                }else{
//                    throw new Exception("Empresa null login");
//                }
//            } catch (Exception e) {
//                log.error("Error login: " + e.getMessage());
//            }
//        }else{
//            log.info("EMAIL NO EXISTE");
//        }
//        log.info("AFUERA LOGIN");
//        return null;
//    }
    @Transactional
    public EmpresaResponse login2(EmpresaRequest empresaRequest) throws Exception {
        Optional<Empresa> optional = empresaRepository.findByEmailEmpresa(empresaRequest.getEmailEmpresa());
        if (optional.isPresent() ) {
            try {

            Empresa empresa = optional.get();
            if(empresa!=null){
                try {
                if(empresaRequest.getClave().equals(empresa.getClave())){
                    return  new EmpresaResponse(empresa.getId(), empresa.getEmailEmpresa(),empresa.getClave(),generateTokenLogin2(empresaRequest));
                }else{
                    throw new Exception("La contraseña es incorrecta");
                }
                } catch (Exception e) {
                    log.error("Error login: " + e.getMessage());
                }
            }else{
                throw new Exception("Empresa null login");
            }
            } catch (Exception e) {
                log.error("Error login: " + e.getMessage());
            }
        }else{
            log.info("EMAIL NO EXISTE");
        }
        log.info("AFUERA LOGIN");
        return null;
    }

    public String generateTokenLogin2(EmpresaRequest empresaRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(empresaRequest.getEmailEmpresa(), empresaRequest.getEmailEmpresa())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token la empresa con email: {}", empresaRequest.getEmailEmpresa());
            throw new Exception("INAVALID");
        }
        return jwtUtil.generateToken(empresaRequest.getEmailEmpresa());
    }



    public EmpresaResponse getEmp(String emailEmpresa) {
        Optional<Empresa> optional = empresaRepository.findByEmailEmpresa(emailEmpresa);
        if (optional.isPresent() ) {
            Empresa empresa = optional.get();
            return new EmpresaResponse(empresa.getId(), empresa.getEmailEmpresa(), empresa.getClave());
        }
        throw new ResponseNotFoundException("Empresa", "emailEmpresa", emailEmpresa);
    }


}