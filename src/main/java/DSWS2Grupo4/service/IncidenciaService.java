package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.IncidenciaRequest;
import DSWS2Grupo4.DTO.IncidenciaResponse;
import DSWS2Grupo4.DTO.IncidenciaTecnicoDTO;
import DSWS2Grupo4.DTO.SeguimientoIncidenciaDTO;
import DSWS2Grupo4.model.*;
import DSWS2Grupo4.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IncidenciaService {

    @Autowired private IncidenciaRepository incidenciaRepo;
    @Autowired private UsuarioSolicitanteRepository usuarioRepo;
    @Autowired private EquipoRepository equipoRepo;
    @Autowired private ProblemaSubcategoriaRepository problemaRepo;
    @Autowired private AsignacionIncidenciaRepository asignacionRepo;
    @Autowired private HistorialEquipoRepository historialEquipoRepo;

    public IncidenciaResponse registrarIncidenciaPublica(IncidenciaRequest req) {
        // Validar equipo
        Equipo equipo = equipoRepo.findByCodigoEquipo(req.getCodigoEquipo())
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        // Validar problema
        ProblemaSubcategoria problemaSubcategoria = problemaRepo.findById(req.getProblemaId())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));

        // Determinar prioridad por dominio de correo
        Integer prioridadUsuarioCalculada = calcularPrioridadPorCorreo(req.getCorreo());

        // Crear o actualizar usuario solicitante
        UsuarioSolicitante usuario = usuarioRepo
                .findByCorreoNumeroAndEquipo_IdEquipo(req.getCorreo(), equipo.getIdEquipo())
                .orElseGet(() -> {
                    UsuarioSolicitante u = new UsuarioSolicitante();
                    u.setCorreoNumero(req.getCorreo());
                    u.setEquipo(equipo);
                    u.setPrioridadUsuario(prioridadUsuarioCalculada);
                    return usuarioRepo.save(u);
                });

        // Crear incidencia
        Incidencia inc = new Incidencia();
        inc.setUsuarioSolicitante(usuario);
        inc.setProblemaSubcategoria(problemaSubcategoria);
        inc.setEstado(EstadoIncidencia.pendiente);
        inc.setUbicacionAtencion(UbicacionAtencion.remoto);
        inc = incidenciaRepo.save(inc);

        Integer prioridadFinal = calcularPrioridadFinal(
                usuario.getPrioridadUsuario(),
                problemaSubcategoria.getPrioridadProblema()
        );

        return new IncidenciaResponse(
                inc.getId(),
                prioridadFinal,
                "Incidencia registrada exitosamente"
        );
    }

    private Integer calcularPrioridadPorCorreo(String correo) {
        if (correo.endsWith("@gerente.com")) return 5;
        if (correo.endsWith("@subgerente.com")) return 3;
        if (correo.endsWith("@empleado.com")) return 1;
        return 1;
    }

    private Integer calcularPrioridadFinal(Integer prioridadUsuario, Integer prioridadProblema) {
        return (prioridadUsuario + prioridadProblema) / 2;
    }

    public List<IncidenciaTecnicoDTO> listarIncidenciasTecnico(Integer tecnicoId, String numeroTicket) {
        List<Incidencia> incidencias;
        if (numeroTicket != null && !numeroTicket.isEmpty()) {
            incidencias = incidenciaRepo.findByTecnicoIdAndTicket(tecnicoId, Long.parseLong(numeroTicket));
        } else {
            incidencias = incidenciaRepo.findByTecnicoId(tecnicoId);
        }

        return incidencias.stream()
                .map(this::convertToTecnicoDTO)
                .collect(Collectors.toList());
    }

    private IncidenciaTecnicoDTO convertToTecnicoDTO(Incidencia incidencia) {
        UsuarioSolicitante usuario = incidencia.getUsuarioSolicitante();
        Integer prioridadProblema = incidencia.getProblemaSubcategoria().getPrioridadProblema();
        Integer prioridadUsuario = usuario.getPrioridadUsuario();

        int prioridadTotal = (prioridadProblema != null ? prioridadProblema : 0) +
                (prioridadUsuario != null ? prioridadUsuario : 0);
        return new IncidenciaTecnicoDTO(
                incidencia.getId(),
                usuario.getCorreoNumero(),
                usuario.getEquipo().getCodigoEquipo(),
                incidencia.getFecha(),
                incidencia.getProblemaSubcategoria().getDescripcionProblema(),
                incidencia.getEstado(),
                prioridadTotal  // Prioridad csumada
        );
    }


    public List<Incidencia> listarIncidencias() {
        return incidenciaRepo.findAll();
    }

    // Obtener por ID
    public Incidencia obtenerPorId(Long id) {
        return incidenciaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada con ID: " + id));
    }

    // Registrar incidencia desde request
    public Incidencia registrarIncidencia(IncidenciaRequest req) {
        IncidenciaResponse response = registrarIncidenciaPublica(req);
        return incidenciaRepo.findById(response.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar la incidencia recién creada"));
    }

    // Guardar incidencia
    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepo.save(incidencia);
    }

    // Actualizar incidencia
    public Incidencia actualizarIncidencia(Long id, Incidencia incidencia) {
        if (!incidenciaRepo.existsById(id)) {
            throw new EntityNotFoundException("Incidencia no encontrada con ID: " + id);
        }
        incidencia.setId(id);
        return incidenciaRepo.save(incidencia);
    }

    // Eliminar incidencia
    public boolean eliminarIncidencia(Long id) {
        if (incidenciaRepo.existsById(id)) {
            incidenciaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    //Consultar Estado Incidencia
    public List<SeguimientoIncidenciaDTO> listarSeguimientoPorCorreo(String correo) {
        List<Incidencia> incidencias = incidenciaRepo.findByUsuarioSolicitante_CorreoNumero(correo);

        return incidencias.stream().map(incidencia -> {
            SeguimientoIncidenciaDTO dto = new SeguimientoIncidenciaDTO();
            dto.setIdIncidencia(incidencia.getId());
            dto.setEstado(incidencia.getEstado().name());
            dto.setModalidadAtencion(incidencia.getUbicacionAtencion().name());
            dto.setFechaRegistro(incidencia.getFecha().toString());
            dto.setProblema(incidencia.getProblemaSubcategoria().getDescripcionProblema());

            // técnico asignado (si existe)
            asignacionRepo.findByIncidencia(incidencia).ifPresent(asig ->
                    dto.setTecnicoAsignado(asig.getTecnico().getEmpleado().getNombre())
            );

            // historial de solución (si existe)
            if (incidencia.getHistorialEquipo() != null) {
                dto.setHistorialSolucion(incidencia.getHistorialEquipo().getSolucion().getSolucionProblema());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}