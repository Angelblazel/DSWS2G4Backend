package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.ActualizarEstadoSolicitudDTO;
import DSWS2Grupo4.DTO.SolicitudRepuestoDTO;
import DSWS2Grupo4.DTO.SolicitudRepuestoRequest;
import DSWS2Grupo4.model.EstadoSolicitudRepuesto;
import DSWS2Grupo4.model.SolicitudRepuesto;
import DSWS2Grupo4.repository.SolicitudRepuestoRepository;
import DSWS2Grupo4.service.SolicitudRepuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes-repuestos")
@CrossOrigin(origins = "*")
public class SolicitudRepuestoController {

    @Autowired
    private SolicitudRepuestoService solicitudService;

    @Autowired
    private SolicitudRepuestoRepository solicitudRepo;

    @GetMapping
    public ResponseEntity<List<SolicitudRepuestoDTO>> listarSolicitudesPendientes() {
        List<SolicitudRepuesto> solicitudes = solicitudRepo.findAll();

        List<SolicitudRepuestoDTO> resultado = solicitudes.stream().map(sol -> {
            SolicitudRepuestoDTO dto = new SolicitudRepuestoDTO();
            dto.setId(sol.getId());
            dto.setEstado(sol.getEstado());
            dto.setFechaSolicitud(sol.getFechaSolicitud());

            // Tomamos solo el primer detalle
            if (sol.getDetalles() != null && !sol.getDetalles().isEmpty()) {
                var detalle = sol.getDetalles().get(0);
                dto.setCantidad(detalle.getCantidad());
                dto.setCodigoRepuesto(detalle.getRepuesto().getCodigoRepuesto());
                dto.setNombreRepuesto(detalle.getRepuesto().getNombre());
            }

            return dto;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

    // Endpoint para registrar solicitud de repuesto
    @PostMapping
    public ResponseEntity<String> registrarSolicitud(@RequestBody SolicitudRepuestoRequest request) {
        try {
            solicitudService.registrarSolicitud(request);
            return ResponseEntity.ok("Solicitud de repuesto registrada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar la solicitud: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEstado(@PathVariable Long id,
                                                   @RequestBody ActualizarEstadoSolicitudDTO dto) {
        try {
            solicitudService.actualizarEstadoSolicitud(id, dto.getNuevoEstado());
            return ResponseEntity.ok("Estado actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
