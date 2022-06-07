package com.tecazuay.complexivog2c2.service.empresa;

import com.tecazuay.complexivog2c2.dto.empresa.EmpresaRequest;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaResponse;
import com.tecazuay.complexivog2c2.dto.empresa.RepresentanteResponse;
import com.tecazuay.complexivog2c2.dto.usuarios.UserRequest;
import com.tecazuay.complexivog2c2.exception.BadRequestException;
import com.tecazuay.complexivog2c2.exception.ResponseNotFoundException;
import com.tecazuay.complexivog2c2.model.Primary.coordinadores.CoordinadorVinculacion;
import com.tecazuay.complexivog2c2.model.Primary.empresa.Empresa;
import com.tecazuay.complexivog2c2.model.Primary.proyecto.ProyectoPPP;
import com.tecazuay.complexivog2c2.model.Primary.usuario.Usuario;
import com.tecazuay.complexivog2c2.model.Secondary.personas.VPersonasppp;
import com.tecazuay.complexivog2c2.repository.Primary.designaciones.CoordinadorVinculacionRepository;
import com.tecazuay.complexivog2c2.repository.Primary.empresa.EmpresaRepository;
import com.tecazuay.complexivog2c2.repository.Primary.proyecto.ProyectoRepository;
import com.tecazuay.complexivog2c2.repository.Primary.usuario.UsuarioRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class EmpresaService implements UserDetailsService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private CoordinadorVinculacionRepository coordinadorVinculacionRepository;
    @Autowired
    private PersonasRepository personasRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;




    public List<EmpresaResponse> listEmpresa() {
        List<Empresa> lista = empresaRepository.findAll();
        return lista.stream().map(empresa -> {
            EmpresaResponse er = new EmpresaResponse();
            er.setId(empresa.getId());
            er.setNombre(empresa.getNombre());
            er.setRepresentante(empresa.getRepresentante());
            er.setEmailEmpresa(empresa.getEmailEmpresa());
            er.setClave(empresa.getClave());
            er.setEmailRepresentante(empresa.getEmailRepresentante());
            er.setTelefonoEmpresa(empresa.getTelefonoEmpresa());
            er.setCelularRepresentante(empresa.getCelularRepresentante());
            er.setFechaCreacion(empresa.getFechaCreacion());
            er.setNombreCoordinador(getNombreCoordinador(empresa.getCoordinadorVinculacion().getId()));
//            er.setNombreAdministrador(empresa.getNombreAdministrador());
//            er.setCedulaAdministrador(empresa.getCedulaAdministrador());
//            er.setCorreoAdministrador(empresa.getCorreoAdministrador());
            er.setCiudad(empresa.getCiudad());
            er.setDireccion(empresa.getDireccion());
            er.setDescripcionEmpresa(empresa.getDescripcionEmpresa());
            return er;
        }).collect(Collectors.toList());
    }

    /**
     * Obtiene el nombre del coordinador que crea la empresa beneficiaria
     *
     * @param id
     * @return nombre del coordinador
     */
    public String getNombreCoordinador(Long id) {
        Optional<CoordinadorVinculacion> optional = coordinadorVinculacionRepository.findById(id);
        if (optional.isPresent()) {
            Optional<VPersonasppp> optionalP = personasRepository.findByCedula(optional.get().getCedula());
            if (optionalP.isPresent()) {
                return optionalP.get().getNombres() + " " + optionalP.get().getApellidos();
            }
        }
        throw new BadRequestException("No se encontró el nombre del Coordinador de Vinculacion");

    }

    /**
     * Lista mediante el nombre a las entidades beneficiarias de bd_vinculacion
     *
     * @param nombre
     * @return list<Empresa>
     */
    public List<EmpresaResponse> listEmpresaNombre(String nombre) {
        List<Empresa> lista = empresaRepository.findByNombreLikeIgnoreCase("%" + nombre + "%");
        return lista.stream().map(empresa -> {
            EmpresaResponse er = new EmpresaResponse();
            er.setId(empresa.getId());
            er.setNombre(empresa.getNombre());
            er.setRepresentante(empresa.getRepresentante());
            er.setEmailEmpresa(empresa.getEmailEmpresa());
            er.setClave(empresa.getClave());
            er.setEmailRepresentante(empresa.getEmailRepresentante());
            er.setTelefonoEmpresa(empresa.getTelefonoEmpresa());
            er.setCelularRepresentante(empresa.getCelularRepresentante());
            er.setFechaCreacion(empresa.getFechaCreacion());
            er.setNombreCoordinador(getNombreCoordinador(empresa.getCoordinadorVinculacion().getId()));
//            er.setNombreAdministrador(empresa.getNombreAdministrador());
//            er.setCedulaAdministrador(empresa.getCedulaAdministrador());
//            er.setCorreoAdministrador(empresa.getCorreoAdministrador());
            er.setCiudad(empresa.getCiudad());
            er.setDireccion(empresa.getDireccion());
            er.setDescripcionEmpresa(empresa.getDescripcionEmpresa());
            return er;
        }).collect(Collectors.toList());
    }


    public Boolean update(EmpresaRequest empresaRequest) {
        Optional<Empresa> optional = empresaRepository.findById(empresaRequest.getId());
        if (optional.isPresent()) {
            optional.get().setNombre(empresaRequest.getNombre());
            optional.get().setRepresentante(empresaRequest.getRepresentante());
            optional.get().setEmailEmpresa(empresaRequest.getEmailEmpresa());
            optional.get().setClave(empresaRequest.getClave());
            optional.get().setEmailRepresentante(empresaRequest.getEmailRepresentante());
            optional.get().setTelefonoEmpresa(empresaRequest.getTelefonoEmpresa());
            optional.get().setCelularRepresentante(empresaRequest.getCelularRepresentante());
//            optional.get().setNombreAdministrador(empresaRequest.getNombreAdministrador());
//            optional.get().setCedulaAdministrador(empresaRequest.getCedulaAdministrador());
//            optional.get().setCorreoAdministrador(empresaRequest.getCorreoAdministrador());
            optional.get().setCiudad(empresaRequest.getCiudad());
            optional.get().setDireccion(empresaRequest.getDireccion());
            optional.get().setDescripcionEmpresa(empresaRequest.getDescripcionEmpresa());
            try {
                Empresa eb = empresaRepository.save(optional.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó la empresa beneficiaria" + ex);
            }
        }
        throw new BadRequestException("No existe la empresa " + empresaRequest.getId());

    }

    /**
     * Metodo de guardar una empresa beneficiaria
     * en la que se valida el
     *
     * @param empresaRequest
     * @return
     */
    public boolean save(EmpresaRequest empresaRequest) {
        Empresa eb = new Empresa();
        eb.setNombre(empresaRequest.getNombre());
        eb.setRepresentante(empresaRequest.getRepresentante());
        eb.setEmailEmpresa(empresaRequest.getEmailEmpresa());
        eb.setClave(empresaRequest.getClave());
        eb.setEmailRepresentante(empresaRequest.getEmailRepresentante());
        eb.setTelefonoEmpresa(empresaRequest.getTelefonoEmpresa());
        eb.setCelularRepresentante(empresaRequest.getCelularRepresentante());
        eb.setFechaCreacion(empresaRequest.getFechaCreacion());
        eb.setCoordinadorVinculacion(getIdCoordinador(empresaRequest.getIdCoordinador()));
//        eb.setNombreAdministrador(empresaRequest.getNombreAdministrador());
//        eb.setCedulaAdministrador(empresaRequest.getCedulaAdministrador());
//        eb.setCorreoAdministrador(empresaRequest.getCorreoAdministrador());
        eb.setCiudad(empresaRequest.getCiudad());
        eb.setDireccion(empresaRequest.getDireccion());
        eb.setDescripcionEmpresa(empresaRequest.getDescripcionEmpresa());
        try {
            empresaRepository.save(eb);
            return true;
        } catch (Exception ex) {
            throw new BadRequestException("No se guardó la empresa beneficiaria" + ex);
        }


    }

    /**
     * Obtene ID de Coordinador a partir del ID de usuario
     *
     * @param id
     * @return
     */
    public CoordinadorVinculacion getIdCoordinador(Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {

            return coordinadorVinculacionRepository.findByUsuario(optional.get()).orElse(new CoordinadorVinculacion());
        }else{
            throw new BadRequestException("No se encontró el id del Coordinador de Vinculacion");
        }

    }


    public EmpresaResponse listEmpresaId(Long id) {
        EmpresaResponse response = new EmpresaResponse();
        Optional<Empresa> optional = empresaRepository.findById(id);
        optional.stream().forEach(e -> {
            response.setId(e.getId());
            response.setNombre(e.getNombre());
            response.setRepresentante(e.getRepresentante());
            response.setEmailEmpresa(e.getEmailEmpresa());
            response.setClave(e.getClave());
            response.setEmailRepresentante(e.getEmailRepresentante());
            response.setTelefonoEmpresa(e.getTelefonoEmpresa());
            response.setCelularRepresentante(e.getCelularRepresentante());
            response.setFechaCreacion(e.getFechaCreacion());
            response.setNombreCoordinador(getNombreCoordinador(e.getCoordinadorVinculacion().getId()));
//            response.setNombreAdministrador(entidad.getNombreAdministrador());
//            response.setCedulaAdministrador(entidad.getCedulaAdministrador());
//            response.setCorreoAdministrador(entidad.getCorreoAdministrador());
            response.setCiudad(e.getCiudad());
            response.setDireccion(e.getDireccion());
            response.setDescripcionEmpresa(e.getDescripcionEmpresa());
            response.setIdCoordinador(e.getCoordinadorVinculacion().getId());
        });
        return response;
    }

    public RepresentanteResponse getRepresentante(Long id){
        Optional<ProyectoPPP> op= proyectoRepository.findById(id);
        if(op.isPresent()){
            Optional<Empresa> optional= empresaRepository.findById(op.get().getEntidadbeneficiaria());
            if(optional.isPresent()){
                RepresentanteResponse r= new RepresentanteResponse();
                r.setNombre(optional.get().getRepresentante());
                return r;
            }
            throw new ResponseNotFoundException("EMPRESA","ID:",op.get().getEntidadbeneficiaria()+"");
        }
        throw new ResponseNotFoundException("PROYECTO","ID",id+"");
    }

    public void deleteById(Long id){
        Optional<Empresa> optional = empresaRepository.findById(id);
        if(optional.isEmpty()){
            throw new BadRequestException("El empresa con el id " + id + ", no existe");
        }
        empresaRepository.deleteById(id);
    }







    private boolean getEmpresa(String emailEmpresa) {

        return  empresaRepository.existsByEmailEmpresa(emailEmpresa);
    }





    //INICIAR SESIÓN
    @Transactional
    public EmpresaResponse login(EmpresaRequest empresaRequest) throws Exception {
        Optional<Empresa> optional = empresaRepository.findByEmailEmpresa(empresaRequest.getEmailEmpresa());
        if (optional.isPresent() ) {
            Empresa empresa = optional.get();
            if(empresa!=null){
                if(empresaRequest.getClave().equals(empresa.getClave())){
                    return  new EmpresaResponse(empresa.getId(), empresa.getEmailEmpresa(),empresa.getClave(),generateTokenLogin(empresaRequest));
                }else{
                    throw new Exception("La contraseña es incorrecta");
                }
            }else{
                throw new Exception("Empresa null login");
            }
        }else{
            log.info("EMAIL NO EXISTE");
        }
        log.info("AFUERA LOGIN");
        return null;
    }

    public String generateTokenLogin(EmpresaRequest empresaRequest) throws Exception {
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

    public EmpresaResponse getEmp(String emailEmpresa) {
        Optional<Empresa> optional = empresaRepository.findByEmailEmpresa(emailEmpresa);
        if (optional.isPresent() ) {
            Empresa empresa = optional.get();
            return new EmpresaResponse(empresa.getId(), empresa.getEmailEmpresa(), empresa.getClave());
        }
        throw new ResponseNotFoundException("Empresa", "emailEmpresa", emailEmpresa);
    }

    @Override
    public UserDetails loadUserByUsername(String emailEmpresa) throws UsernameNotFoundException {
        Optional<Empresa> empresa = empresaRepository.findByEmailEmpresa(emailEmpresa);
        return new org.springframework.security.core.userdetails.User(empresa.get().getEmailEmpresa(), empresa.get().getEmailEmpresa(), new ArrayList<>());

    }
}
