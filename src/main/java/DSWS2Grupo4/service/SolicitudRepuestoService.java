package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.*;
import DSWS2Grupo4.model.*;
import DSWS2Grupo4.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudRepuestoService {

    @Autowired
    private SolicitudRepuestoRepository solicitudRepo;

    @Autowired
    private DetalleSolicitudRepuestoRepository detalleRepo;

    @Autowired
    private TecnicoRepository tecnicoRepo;

    @Autowired
    private IncidenciaRepository incidenciaRepo;

    @Autowired
    private RepuestoRepository repuestoRepo;

    @Transactional
    public void registrarSolicitud(SolicitudRepuestoRequest request) {
        // Validar técnico usando ID de empleado en lugar de ID de técnico
        Tecnico tecnico = tecnicoRepo.findByEmpleadoId(request.getIdTecnico().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado para el empleado ID: " + request.getIdTecnico()));

        Incidencia incidencia = incidenciaRepo.findById(request.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));
        
        //Validando que el técnico esté asignado a la incidencia
        if (incidencia.getAsignacion() == null ||
                !incidencia.getAsignacion().getTecnico().getId().equals(tecnico.getId())) {
            throw new IllegalArgumentException("El técnico no está asignado a la incidencia.");
        }
        
        // Crear cabecera
        SolicitudRepuesto solicitud = new SolicitudRepuesto();
        solicitud.setTecnico(tecnico);
        solicitud.setIncidencia(incidencia);
        solicitud.setEstado(EstadoSolicitudRepuesto.PENDIENTE);
        solicitud = solicitudRepo.save(solicitud);

        // Crear cada detalle
        for (DetalleSolicitudDTO detalleDTO : request.getDetalles()) {
            Repuesto repuesto = repuestoRepo.findById(detalleDTO.getIdRepuesto())
                    .orElseThrow(() -> new EntityNotFoundException("Repuesto no encontrado con ID: " + detalleDTO.getIdRepuesto()));

            if (detalleDTO.getCantidad() > repuesto.getCantidad()) {
                throw new IllegalArgumentException("Cantidad solicitada de repuesto '" + repuesto.getNombre() + "' excede el stock.");
            }

            DetalleSolicitudRepuesto detalle = new DetalleSolicitudRepuesto();
            detalle.setSolicitud(solicitud);
            detalle.setRepuesto(repuesto);
            detalle.setCantidad(detalleDTO.getCantidad());
            
            detalleRepo.save(detalle);
        }
    }

    @Transactional
    public void actualizarEstadoSolicitud(Long id, String nuevoEstado) {
        SolicitudRepuesto solicitud = solicitudRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        EstadoSolicitudRepuesto estado = EstadoSolicitudRepuesto.valueOf(nuevoEstado.toUpperCase());

        if (estado == EstadoSolicitudRepuesto.ATENDIDO) {
            // Descontar
            solicitud.getDetalles().forEach(detalle -> {
                Repuesto repuesto = repuestoRepo.findById(detalle.getRepuesto().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Repuesto no encontrado con id: " + detalle.getRepuesto().getId()));

                int nuevaCantidad = repuesto.getCantidad() - detalle.getCantidad();
                if (nuevaCantidad < 0) {
                    throw new IllegalArgumentException(
                            "Stock insuficiente para el repuesto " + repuesto.getCodigoRepuesto());
                }
                repuesto.setCantidad(nuevaCantidad);
                repuestoRepo.save(repuesto);
            });
        }
        solicitud.setEstado(estado);
        solicitudRepo.save(solicitud);
    }

    public List<RepuestoDTO> listarRepuestos() {
        return repuestoRepo.findAll().stream()
                .map(r -> new RepuestoDTO(r.getId(), r.getCodigoRepuesto(), r.getNombre(), r.getDescripcion(), r.getCantidad()))
                .collect(Collectors.toList());
    }

    public List<RepuestoDTO> buscarRepuestosPorNombre(String nombre) {
        return repuestoRepo.findByNombreContainingIgnoreCase(nombre).stream()
                .map(r -> new RepuestoDTO(r.getId(), r.getCodigoRepuesto(), r.getNombre(), r.getDescripcion(), r.getCantidad()))
                .collect(Collectors.toList());
    }

    public List<SolicitudRepuesto> listarSolicitudesPendientes() {
        return solicitudRepo.findByEstado(EstadoSolicitudRepuesto.PENDIENTE);
    }
}
