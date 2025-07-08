package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.*;
import DSWS2Grupo4.model.HistorialEquipo;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.model.SolucionSubcategoria;
import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.service.AlertaIncidenciaService;
import DSWS2Grupo4.service.IncidenciaService;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/incidencias")
@CrossOrigin(origins = "*")  // Para facilitar las pruebas
public class IncidenciaController {

    @Autowired
    private IncidenciaService incService;

    @Autowired
    private AlertaIncidenciaService alertaService;

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Incidencia>> listar() {
        return ResponseEntity.ok(incService.listarIncidencias());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> getById(@PathVariable Long id) {
        try {
            Incidencia inc = incService.obtenerPorId(id);
            return ResponseEntity.ok(inc);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear pública (sin login)
    @PostMapping("/publica")
    public ResponseEntity<?> registrarIncidenciaPublica(@RequestBody IncidenciaRequest req) {
        try {
            IncidenciaResponse response = incService.registrarIncidenciaPublica(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Loguear el error completo
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    // Crear (con entidad completa)
    @PostMapping
    public ResponseEntity<Incidencia> crear(@RequestBody Incidencia inc) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(incService.guardarIncidencia(inc));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizar(@PathVariable Long id, @RequestBody Incidencia inc) {
        try {
            Incidencia updated = incService.actualizarIncidencia(id, inc);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return incService.eliminarIncidencia(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Seguimiento de incidencia
    @GetMapping("/seguimiento")
    public ResponseEntity<List<SeguimientoIncidenciaDTO>> seguimiento(@RequestParam String correo) {
        return ResponseEntity.ok(incService.listarSeguimientoPorCorreo(correo));
    }

    // Envio de alerta
    @PostMapping("/alerta")
    public ResponseEntity<Map<String, String>> registrarAlerta(@RequestBody AlertaRequest req) {
        try {
            System.out.println("========== ALERTA RECIBIDA ==========");
            System.out.println("ID: " + req.getIdIncidencia());
            System.out.println("Motivo: " + req.getMotivo());

            alertaService.registrarAlerta(req);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Alerta registrada correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al registrar alerta: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    //Buscar por ID Publico
    @GetMapping("/publica/{id}")
    public ResponseEntity<IncidenciaPublicaResponse> obtenerIncidenciaPublica(@PathVariable Long id) {
        try {
            Incidencia inc = incService.obtenerPorId(id);

            IncidenciaPublicaResponse response = new IncidenciaPublicaResponse();
            response.setIdIncidencia(inc.getId());
            response.setEstado(inc.getEstado().toString());
            response.setFechaRegistro(inc.getFecha());
            response.setCorreoSolicitante(inc.getUsuarioSolicitante().getCorreoNumero());
            
            response.setCategoriaProblema(inc.getProblemaSubcategoria().getSubcategoria().getCategoria().getNombreCategoria());
            response.setSubCategoria(inc.getProblemaSubcategoria().getSubcategoria().getNombreSubcategoria());
            response.setDescripcionProblema(inc.getProblemaSubcategoria().getDescripcionProblema());
            
            response.setPrioridad(String.valueOf(inc.getUsuarioSolicitante().getPrioridadUsuario()));
            response.setCodigoEquipo(inc.getUsuarioSolicitante().getEquipo().getCodigoEquipo());
            
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Obtener soluciones para una incidencia
    @GetMapping("/{idIncidencia}/soluciones")
    public ResponseEntity<List<SolucionSubcategoria>> obtenerSoluciones(@PathVariable Long idIncidencia) {
        List<SolucionSubcategoria> soluciones = incService.obtenerSolucionesPorIncidencia(idIncidencia);
        return ResponseEntity.ok(soluciones);
    }

    // Registrar solución en historial para una incidencia
    @PostMapping("/solucion")
    public ResponseEntity<HistorialEquipo> registrarSolucion(@RequestBody SolucionRequest request) {
        HistorialEquipo historial = incService.registrarSolucion(request);
        return ResponseEntity.ok(historial);
    }

    // Generar reporte de incidencias
    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteIncidenciaDTO>> generarReporte(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<ReporteIncidenciaDTO> reporte = incService.generarReporteIncidencias(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    // Editar incidencia por usuario solicitante
    @PutMapping("/editar-publica/{id}")
    public ResponseEntity<?> editarIncidenciaPublica(
            @PathVariable Long id,
            @RequestParam String correo,
            @RequestBody IncidenciaRequest request) {
        try {
            Incidencia incidenciaActualizada = incService.editarIncidenciaPorSolicitante(id, correo, request);
            return ResponseEntity.ok(incidenciaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}