package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.*;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.service.AlertaIncidenciaService;
import DSWS2Grupo4.service.IncidenciaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
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
    public ResponseEntity<String> registrarAlerta(@RequestBody AlertaRequest req) {
        try {
            alertaService.registrarAlerta(req);
            return ResponseEntity.ok("Alerta registrada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //Buscar por ID Publico
    @GetMapping("/publica/{id}")
    public ResponseEntity<IncidenciaPublicaResponse> obtenerIncidenciaPublica(@PathVariable Long id) {
        try {
            Incidencia inc = incService.obtenerPorId(id);

            IncidenciaPublicaResponse response = new IncidenciaPublicaResponse();
            response.setId(inc.getId());
            response.setEstado(inc.getEstado().toString());
            response.setFechaRegistro(inc.getFecha());
            response.setCorreoSolicitante(inc.getUsuarioSolicitante().getCorreoNumero());
            response.setDescripcionProblema(inc.getProblemaSubcategoria().getDescripcionProblema());

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}