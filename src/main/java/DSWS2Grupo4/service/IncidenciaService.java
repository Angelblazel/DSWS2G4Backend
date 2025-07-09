package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.*;
import DSWS2Grupo4.model.*;
import DSWS2Grupo4.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    @Autowired private ProblemaSubcategoriaRepository problemaRepo;
    @Autowired private AsignacionIncidenciaRepository asignacionRepo;
    @Autowired private HistorialEquipoRepository historialEquipoRepo;
    @Autowired private SolucionSubcategoriaRepository solucionRepo;


    public IncidenciaResponse registrarIncidenciaPublica(IncidenciaRequest req) {
        // Buscar usuario existente por correo
        UsuarioSolicitante usuario = usuarioRepo.findByCorreoNumero(req.getCorreo())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con correo: " + req.getCorreo() +
                        ". Debe estar registrado en el sistema previamente."));

        // Validar que el usuario tenga un equipo asignado
        if (usuario.getEquipo() == null) {
            throw new EntityNotFoundException("El usuario no tiene un equipo asignado. Contacte al administrador.");
        }

        // Validar problema
        ProblemaSubcategoria problemaSubcategoria = problemaRepo.findById(req.getProblemaId())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));

        // Crear incidencia
        Incidencia inc = new Incidencia();
        inc.setUsuarioSolicitante(usuario);
        inc.setProblemaSubcategoria(problemaSubcategoria);
        inc.setEstado(EstadoIncidencia.pendiente);
        inc.setUbicacionAtencion(UbicacionAtencion.remoto);
        inc.setFecha(LocalDateTime.now());
        inc = incidenciaRepo.save(inc);

        Integer prioridadFinal = calcularPrioridadFinal(
                0,
                problemaSubcategoria.getPrioridadProblema()
        );

        return new IncidenciaResponse(
                inc.getId(),
                prioridadFinal,
                "Incidencia registrada exitosamente"
        );
    }

    private Integer calcularPrioridadFinal(Integer prioridadUsuario, Integer prioridadProblema) {
        int priorUser = prioridadUsuario != null ? prioridadUsuario : 1;
        int priorProb = prioridadProblema != null ? prioridadProblema : 1;
        return (priorUser + priorProb) / 2;
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

        int prioridadTotal = (prioridadProblema != null ? prioridadProblema : 0);
        
        return new IncidenciaTecnicoDTO(
                incidencia.getId(),
                usuario.getCorreoNumero(),
                usuario.getEquipo().getCodigoEquipo(),
                incidencia.getFecha(),
                incidencia.getProblemaSubcategoria().getDescripcionProblema(),
                incidencia.getEstado(),
                prioridadTotal
        );
    }

    public List<Incidencia> listarIncidencias() {
        return incidenciaRepo.findAll();
    }

    public Incidencia obtenerPorId(Long id) {
        return incidenciaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada con ID: " + id));
    }

    public Incidencia registrarIncidencia(IncidenciaRequest req) {
        IncidenciaResponse response = registrarIncidenciaPublica(req);
        return incidenciaRepo.findById(response.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar la incidencia recién creada"));
    }

    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepo.save(incidencia);
    }

    public Incidencia actualizarIncidencia(Long id, Incidencia incidencia) {
        if (!incidenciaRepo.existsById(id)) {
            throw new EntityNotFoundException("Incidencia no encontrada con ID: " + id);
        }

        incidencia.setId(id);
        incidencia.setFecha(LocalDateTime.now());

        return incidenciaRepo.save(incidencia);
    }

    public boolean eliminarIncidencia(Long id) {
        if (incidenciaRepo.existsById(id)) {
            incidenciaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<SeguimientoIncidenciaDTO> listarSeguimientoPorCorreo(String correo) {
        List<Incidencia> incidencias = incidenciaRepo.findByUsuarioSolicitante_CorreoNumero(correo);

        return incidencias.stream().map(incidencia -> {
            SeguimientoIncidenciaDTO dto = new SeguimientoIncidenciaDTO();
            dto.setIdIncidencia(incidencia.getId());
            dto.setEstado(incidencia.getEstado().name());
            dto.setModalidadAtencion(incidencia.getUbicacionAtencion().name());
            dto.setFechaRegistro(incidencia.getFecha().toString());
            dto.setProblema(incidencia.getProblemaSubcategoria().getDescripcionProblema());

            // Técnico asignado (si existe)
            asignacionRepo.findByIncidencia(incidencia).ifPresent(asig ->
                    dto.setTecnicoAsignado(asig.getTecnico().getEmpleado().getUsername())
            );

            // Historial de solución (si existe)
            if (incidencia.getHistorialEquipo() != null) {
                dto.setHistorialSolucion(incidencia.getHistorialEquipo().getSolucion().getSolucionProblema());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public List<SolucionSubcategoria> obtenerSolucionesPorIncidencia(Long idIncidencia) {
        Incidencia incidencia = incidenciaRepo.findById(idIncidencia)
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));

        Long idProblema = incidencia.getProblemaSubcategoria().getId();

        return solucionRepo.findByProblema_Id(idProblema);
    }

    public HistorialEquipo registrarSolucion(SolucionRequest request) {
        // Buscar la incidencia
        Incidencia incidencia = incidenciaRepo.findById(request.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));

        // Buscar la solución
        SolucionSubcategoria solucion = solucionRepo.findById(request.getIdSolucion())
                .orElseThrow(() -> new EntityNotFoundException("Solución no encontrada"));

        // Actualizar modalidad de atención si está presente
        if (request.getModalidadAtencion() != null) {
            try {
                UbicacionAtencion modalidad = UbicacionAtencion.valueOf(request.getModalidadAtencion().toLowerCase());
                incidencia.setUbicacionAtencion(modalidad);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Modalidad de atención inválida: " + request.getModalidadAtencion());
            }
        }

        // Actualizar estado si está presente
        if (request.getEstado() != null) {
            try {
                EstadoIncidencia nuevoEstado = EstadoIncidencia.valueOf(request.getEstado().toLowerCase());
                incidencia.setEstado(nuevoEstado);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado de incidencia inválido: " + request.getEstado());
            }
        }

        // Guardar cambios en la incidencia
        incidenciaRepo.save(incidencia);

        // Crear o actualizar historial
        HistorialEquipo historial = historialEquipoRepo.findByIncidencia(incidencia).orElse(new HistorialEquipo());
        historial.setIncidencia(incidencia);
        historial.setSolucion(solucion);
        historial.setPalabrasClave(request.getPalabrasClave());
        historial.setFechaSolucion(LocalDateTime.now());

        return historialEquipoRepo.save(historial);
    }

    public List<ReporteIncidenciaDTO> generarReporteIncidencias(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Incidencia> incidencias;

        if (fechaInicio != null && fechaFin != null) {
            incidencias = incidenciaRepo.findByFechaBetween(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            incidencias = incidenciaRepo.findByFechaGreaterThanEqual(fechaInicio);
        } else if (fechaFin != null) {
            incidencias = incidenciaRepo.findByFechaLessThanEqual(fechaFin);
        } else {
            incidencias = incidenciaRepo.findAll();
        }

        return incidencias.stream()
                .map(this::convertToReporteDTO)
                .collect(Collectors.toList());
    }

    public Incidencia editarIncidenciaPorSolicitante(Long idIncidencia, String correoSolicitante, IncidenciaRequest nuevosdatos) {
        Incidencia incidencia = incidenciaRepo.findById(idIncidencia)
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));

        // Verificar que el correo coincida con el solicitante
        if (!incidencia.getUsuarioSolicitante().getCorreoNumero().equals(correoSolicitante)) {
            throw new IllegalStateException("No tiene permisos para editar esta incidencia");
        }

        // Verificar que la incidencia no esté asignada
        if (incidencia.getAsignacion() != null) {
            throw new IllegalStateException("No se puede editar una incidencia que ya ha sido asignada a un técnico");
        }

        // Verificar que esté en estado pendiente
        if (incidencia.getEstado() != EstadoIncidencia.pendiente) {
            throw new IllegalStateException("Solo se pueden editar incidencias en estado pendiente");
        }

        // Actualizar el problema si se proporciona
        if (nuevosdatos.getProblemaId() != null) {
            ProblemaSubcategoria nuevoProblema = problemaRepo.findById(nuevosdatos.getProblemaId())
                    .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            incidencia.setProblemaSubcategoria(nuevoProblema);
        }

        return incidenciaRepo.save(incidencia);
    }

    private ReporteIncidenciaDTO convertToReporteDTO(Incidencia incidencia) {
        String tecnicoAsignado = null;
        if (incidencia.getAsignacion() != null && incidencia.getAsignacion().getTecnico() != null) {
            tecnicoAsignado = incidencia.getAsignacion().getTecnico().getEmpleado().getUsername();
        }

        Integer prioridad = 0;
        if (incidencia.getProblemaSubcategoria().getPrioridadProblema() != null) {
            prioridad += incidencia.getProblemaSubcategoria().getPrioridadProblema();
        }

        return new ReporteIncidenciaDTO(
                incidencia.getId(),
                incidencia.getUsuarioSolicitante().getCorreoNumero(),
                incidencia.getUsuarioSolicitante().getEquipo().getCodigoEquipo(),
                incidencia.getProblemaSubcategoria().getDescripcionProblema(),
                incidencia.getEstado().toString(),
                incidencia.getUbicacionAtencion().toString(),
                incidencia.getFecha(),
                tecnicoAsignado,
                prioridad,
                incidencia.getProblemaSubcategoria().getSubcategoria().getCategoria().getNombreCategoria(),
                incidencia.getProblemaSubcategoria().getSubcategoria().getNombreSubcategoria()
        );
    }

}