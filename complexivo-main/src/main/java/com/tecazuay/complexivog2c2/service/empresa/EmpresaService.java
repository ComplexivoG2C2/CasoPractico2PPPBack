package com.tecazuay.complexivog2c2.service.empresa;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaRequest;
import com.tecazuay.complexivog2c2.dto.empresa.EmpresaResponse;
import com.tecazuay.complexivog2c2.dto.empresa.RepresentanteResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {
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


    public List<EmpresaResponse> listEntidad() {
        List<Empresa> lista = empresaRepository.findAll();
        return lista.stream().map(e -> {
            EmpresaResponse er = new EmpresaResponse();
            er.setIdEntidad(e.getId());
            er.setNombre(e.getNombre());
            er.setRepresentante(e.getRepresentante());
            er.setEmailEntidad(e.getEmailEntidad());
            er.setEmailRepresentante(e.getEmailRepresentante());
            er.setTelefonoEntidad(e.getTelefonoEntidad());
            er.setCelularRepresentante(e.getCelularRepresentante());
            er.setFechaCreacion(e.getFechaCreacion());
            er.setNombreCoordinador(getNombreCoordinador(e.getCoordinadorVinculacion().getId()));
            er.setNombreAdministrador(e.getNombreAdministrador());
            er.setCedulaAdministrador(e.getCedulaAdministrador());
            er.setCorreoAdministrador(e.getCorreoAdministrador());
            er.setCiudad(e.getCiudad());
            er.setDireccion(e.getDireccion());
            er.setDescripcionEmpresa(e.getDescripcionEmpresa());
            return er;
        }).collect(Collectors.toList());
    }

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


    public List<EmpresaResponse> listEntidadNombre(String nombre) {
        List<Empresa> lista = empresaRepository.findByNombreLikeIgnoreCase("%" + nombre + "%");
        return lista.stream().map(e -> {
            EmpresaResponse er = new EmpresaResponse();
            er.setIdEntidad(e.getId());
            er.setNombre(e.getNombre());
            er.setRepresentante(e.getRepresentante());
            er.setEmailEntidad(e.getEmailEntidad());
            er.setEmailRepresentante(e.getEmailRepresentante());
            er.setTelefonoEntidad(e.getTelefonoEntidad());
            er.setCelularRepresentante(e.getCelularRepresentante());
            er.setFechaCreacion(e.getFechaCreacion());
            er.setNombreCoordinador(getNombreCoordinador(e.getCoordinadorVinculacion().getId()));
            er.setNombreAdministrador(e.getNombreAdministrador());
            er.setCedulaAdministrador(e.getCedulaAdministrador());
            er.setCorreoAdministrador(e.getCorreoAdministrador());
            er.setCiudad(e.getCiudad());
            er.setDireccion(e.getDireccion());
            er.setDescripcionEmpresa(e.getDescripcionEmpresa());
            return er;
        }).collect(Collectors.toList());
    }


    public Boolean update(EmpresaRequest entidadRequest) {
        Optional<Empresa> optional = empresaRepository.findById(entidadRequest.getId());
        if (optional.isPresent()) {
            optional.get().setNombre(entidadRequest.getNombre());
            optional.get().setRepresentante(entidadRequest.getRepresentante());
            optional.get().setEmailEntidad(entidadRequest.getEmailEntidad());
            optional.get().setEmailRepresentante(entidadRequest.getEmailRepresentante());
            optional.get().setTelefonoEntidad(entidadRequest.getTelefonoEntidad());
            optional.get().setCelularRepresentante(entidadRequest.getCelularRepresentante());
            optional.get().setNombreAdministrador(entidadRequest.getNombreAdministrador());
            optional.get().setCedulaAdministrador(entidadRequest.getCedulaAdministrador());
            optional.get().setCorreoAdministrador(entidadRequest.getCorreoAdministrador());
            optional.get().setCiudad(entidadRequest.getCiudad());
            optional.get().setDireccion(entidadRequest.getDireccion());
            optional.get().setDescripcionEmpresa(entidadRequest.getDescripcionEmpresa());
            try {
                Empresa eb = empresaRepository.save(optional.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó la empresa" + ex);
            }
        }
        throw new BadRequestException("No existe la empresa" + entidadRequest.getId());

    }


    public boolean save(EmpresaRequest entidadRequest) {
        Empresa eb = new Empresa();
        eb.setNombre(entidadRequest.getNombre());
        eb.setRepresentante(entidadRequest.getRepresentante());
        eb.setEmailEntidad(entidadRequest.getEmailEntidad());
        eb.setEmailRepresentante(entidadRequest.getEmailRepresentante());
        eb.setTelefonoEntidad(entidadRequest.getTelefonoEntidad());
        eb.setCelularRepresentante(entidadRequest.getCelularRepresentante());
        eb.setFechaCreacion(entidadRequest.getFechaCreacion());
        eb.setCoordinadorVinculacion(getIdCoordinador(entidadRequest.getIdCoordinador()));
        eb.setNombreAdministrador(entidadRequest.getNombreAdministrador());
        eb.setCedulaAdministrador(entidadRequest.getCedulaAdministrador());
        eb.setCorreoAdministrador(entidadRequest.getCorreoAdministrador());
        eb.setCiudad(entidadRequest.getCiudad());
        eb.setDireccion(entidadRequest.getDireccion());
        eb.setDescripcionEmpresa(entidadRequest.getDescripcionEmpresa());
        try {
            empresaRepository.save(eb);
            return true;
        } catch (Exception ex) {
            throw new BadRequestException("No se guardó la empresa" + ex);
        }


    }


    public CoordinadorVinculacion getIdCoordinador(Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {

            return coordinadorVinculacionRepository.findByUsuario(optional.get()).orElse(new CoordinadorVinculacion());
        }else{
            throw new BadRequestException("No se encontró el id del Coordinador de Vinculacion");
        }

    }


    public EmpresaResponse listEntidadId(Long id) {
        EmpresaResponse response = new EmpresaResponse();
        Optional<Empresa> optional = empresaRepository.findById(id);
        optional.stream().forEach(entidad -> {
            response.setIdEntidad(entidad.getId());
            response.setNombre(entidad.getNombre());
            response.setRepresentante(entidad.getRepresentante());
            response.setEmailEntidad(entidad.getEmailEntidad());
            response.setEmailRepresentante(entidad.getEmailRepresentante());
            response.setTelefonoEntidad(entidad.getTelefonoEntidad());
            response.setCelularRepresentante(entidad.getCelularRepresentante());
            response.setFechaCreacion(entidad.getFechaCreacion());
            response.setNombreCoordinador(getNombreCoordinador(entidad.getCoordinadorVinculacion().getId()));
            response.setNombreAdministrador(entidad.getNombreAdministrador());
            response.setCedulaAdministrador(entidad.getCedulaAdministrador());
            response.setCorreoAdministrador(entidad.getCorreoAdministrador());
            response.setCiudad(entidad.getCiudad());
            response.setDireccion(entidad.getDireccion());
            response.setDescripcionEmpresa(entidad.getDescripcionEmpresa());
            response.setIdCoordinador(entidad.getCoordinadorVinculacion().getId());
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
            throw new BadRequestException("La empresa con el id " + id + ", no existe");
        }
        empresaRepository.deleteById(id);
    }
}
