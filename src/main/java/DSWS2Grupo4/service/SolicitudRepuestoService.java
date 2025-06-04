package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.DetalleSolicitudDTO;
import DSWS2Grupo4.DTO.SolicitudRepuestoRequest;
import DSWS2Grupo4.model.*;
import DSWS2Grupo4.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Validar técnico e incidencia
        Tecnico tecnico = tecnicoRepo.findById(request.getIdTecnico())
                .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado"));

        Incidencia incidencia = incidenciaRepo.findById(request.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));

        // Crear cabecera
        SolicitudRepuesto solicitud = new SolicitudRepuesto();
        solicitud.setTecnico(tecnico);
        solicitud.setIncidencia(incidencia);
        //solicitud.setEstado(EstadoSolicitudRepuesto.pendiente);
        solicitud.setEstado("PENDIENTE");
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

    public void actualizarEstadoSolicitud(Long id, String nuevoEstado) {
        SolicitudRepuesto solicitud = solicitudRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        EstadoSolicitudRepuesto estado = EstadoSolicitudRepuesto.valueOf(nuevoEstado.toUpperCase());

        //solicitud.setEstado(EstadoSolicitudRepuesto.pendiente);
        solicitud.setEstado("PENDIENTE");
        solicitudRepo.save(solicitud);
    }

}
