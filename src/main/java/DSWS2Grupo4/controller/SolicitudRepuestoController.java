package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.ActualizarEstadoSolicitudDTO;
import DSWS2Grupo4.DTO.DetalleRepuestoDTO;
import DSWS2Grupo4.DTO.RepuestoDTO;
import DSWS2Grupo4.DTO.SolicitudRepuestoDTO;
import DSWS2Grupo4.DTO.SolicitudRepuestoRequest;
import DSWS2Grupo4.model.EstadoSolicitudRepuesto;
import DSWS2Grupo4.model.SolicitudRepuesto;
import DSWS2Grupo4.repository.SolicitudRepuestoRepository;
import DSWS2Grupo4.service.SolicitudRepuestoService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/solicitudes-repuestos")
@CrossOrigin(origins = "*")
public class SolicitudRepuestoController {

    @Autowired
    private SolicitudRepuestoService solicitudService;

    @Autowired
    private SolicitudRepuestoRepository solicitudRepo;

    // Endpoint para registrar solicitud de repuesto
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarSolicitud(@RequestBody SolicitudRepuestoRequest request) {
        try {
            solicitudService.registrarSolicitud(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Solicitud de repuestos registrada exitosamente");
            response.put("success", true);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error interno del servidor: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/repuestos")
    public ResponseEntity<List<RepuestoDTO>> listarRepuestos() {
        try {
            return ResponseEntity.ok(solicitudService.listarRepuestos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/repuestos/buscar")
    public ResponseEntity<List<RepuestoDTO>> buscarRepuestos(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(solicitudService.buscarRepuestosPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudRepuesto>> listarSolicitudesPendientes() {
        try {
            return ResponseEntity.ok(solicitudService.listarSolicitudesPendientes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudRepuestoDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            solicitudService.actualizarEstadoSolicitud(id, estado);

            // Obtener la solicitud actualizada
            SolicitudRepuesto solicitudActualizada = solicitudRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

            // Convertir a DTO
            SolicitudRepuestoDTO dto = new SolicitudRepuestoDTO();
            dto.setId(solicitudActualizada.getId());
            dto.setEstado(solicitudActualizada.getEstado().toString());
            dto.setFechaSolicitud(solicitudActualizada.getFechaSolicitud());

            if (solicitudActualizada.getTecnico() != null && solicitudActualizada.getTecnico().getEmpleado() != null) {
                dto.setNombreTecnico(solicitudActualizada.getTecnico().getEmpleado().getUsername());
            }

            if (solicitudActualizada.getIncidencia() != null) {
                dto.setIdIncidencia(solicitudActualizada.getIncidencia().getId());
            }

            if (solicitudActualizada.getDetalles() != null && !solicitudActualizada.getDetalles().isEmpty()) {
                var detalle = solicitudActualizada.getDetalles().get(0);
                dto.setCantidad(detalle.getCantidad());
                dto.setCodigoRepuesto(detalle.getRepuesto().getCodigoRepuesto());
                dto.setNombreRepuesto(detalle.getRepuesto().getNombre());
                dto.setDescripcionRepuesto(detalle.getRepuesto().getDescripcion());
            }

            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SolicitudRepuestoDTO>> listarSolicitudesPendientesDTO() {
        try {
            List<SolicitudRepuesto> solicitudes = solicitudRepo.findAll();

            List<SolicitudRepuestoDTO> resultado = solicitudes.stream().map(sol -> {
                SolicitudRepuestoDTO dto = new SolicitudRepuestoDTO();
                dto.setId(sol.getId());
                dto.setEstado(sol.getEstado().toString());
                dto.setFechaSolicitud(sol.getFechaSolicitud());

                // Agregar información del técnico
                if (sol.getTecnico() != null && sol.getTecnico().getEmpleado() != null) {
                    dto.setNombreTecnico(sol.getTecnico().getEmpleado().getUsername());
                }

                // Agregar información de la incidencia
                if (sol.getIncidencia() != null) {
                    dto.setIdIncidencia(sol.getIncidencia().getId());
                }

                // Agregar todos los detalles
                if (sol.getDetalles() != null && !sol.getDetalles().isEmpty()) {
                    List<DetalleRepuestoDTO> detallesDTO = sol.getDetalles().stream().map(detalle -> {
                        DetalleRepuestoDTO detalleDTO = new DetalleRepuestoDTO();
                        detalleDTO.setCodigoRepuesto(detalle.getRepuesto().getCodigoRepuesto());
                        detalleDTO.setNombreRepuesto(detalle.getRepuesto().getNombre());
                        detalleDTO.setDescripcionRepuesto(detalle.getRepuesto().getDescripcion());
                        detalleDTO.setCantidad(detalle.getCantidad());
                        return detalleDTO;
                    }).toList();

                    dto.setDetalles(detallesDTO);

                    // Para compatibilidad, tomar el primer detalle
                    var primerDetalle = sol.getDetalles().get(0);
                    dto.setCantidad(primerDetalle.getCantidad());
                    dto.setCodigoRepuesto(primerDetalle.getRepuesto().getCodigoRepuesto());
                    dto.setNombreRepuesto(primerDetalle.getRepuesto().getNombre());
                    dto.setDescripcionRepuesto(primerDetalle.getRepuesto().getDescripcion());
                }

                return dto;
            }).toList();

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEstadoOld(@PathVariable Long id,
            @RequestBody ActualizarEstadoSolicitudDTO dto) {
        try {
            solicitudService.actualizarEstadoSolicitud(id, dto.getNuevoEstado());
            return ResponseEntity.ok("Estado de solicitud actualizado correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    // Editar solicitud por técnico
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarSolicitudPorTecnico(
            @PathVariable Long id,
            @RequestParam Integer idEmpleadoTecnico,
            @RequestBody SolicitudRepuestoRequest request) {
        try {
            solicitudService.editarSolicitudPorTecnico(id, idEmpleadoTecnico, request);
            return ResponseEntity.ok("Solicitud actualizada correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    // Obtener solicitudes por técnico
    @GetMapping("/tecnico/{idEmpleado}")
    @PreAuthorize("hasAuthority('TECNICO')")
    public ResponseEntity<List<SolicitudRepuestoDTO>> obtenerSolicitudesPorTecnico(@PathVariable Integer idEmpleado) {
        try {
            // QUITAR LOGS DE DEBUG
            List<SolicitudRepuesto> solicitudes = solicitudService.obtenerSolicitudesPorTecnico(idEmpleado);

            // FILTRAR SOLO PENDIENTES
            List<SolicitudRepuesto> solicitudesPendientes = solicitudes.stream()
                    .filter(sol -> sol.getEstado() == EstadoSolicitudRepuesto.PENDIENTE)
                    .collect(Collectors.toList());

            List<SolicitudRepuestoDTO> resultado = solicitudesPendientes.stream().map(sol -> {
                SolicitudRepuestoDTO dto = new SolicitudRepuestoDTO();
                dto.setId(sol.getId());
                dto.setIdIncidencia(sol.getIncidencia().getId());
                dto.setEstado(sol.getEstado().toString());
                dto.setFechaSolicitud(sol.getFechaSolicitud());

                // Mapear detalles
                List<DetalleRepuestoDTO> detalles = sol.getDetalles().stream().map(detalle -> {
                    DetalleRepuestoDTO detalleDTO = new DetalleRepuestoDTO();
                    detalleDTO.setIdRepuesto(detalle.getRepuesto().getId());
                    detalleDTO.setCodigoRepuesto(detalle.getRepuesto().getCodigoRepuesto());
                    detalleDTO.setNombreRepuesto(detalle.getRepuesto().getNombre());
                    detalleDTO.setDescripcionRepuesto(detalle.getRepuesto().getDescripcion());
                    detalleDTO.setCantidad(detalle.getCantidad());
                    return detalleDTO;
                }).collect(Collectors.toList());

                dto.setDetalles(detalles);
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}