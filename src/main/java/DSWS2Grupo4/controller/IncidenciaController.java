package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.IncidenciaRequest;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.service.IncidenciaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incService;

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Incidencia>> listar() {
        return ResponseEntity.ok(incService.listarIncidencias());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> getById(@PathVariable Long id) {
        Incidencia inc = incService.obtenerPorId(id);
        return inc != null ? ResponseEntity.ok(inc) : ResponseEntity.notFound().build();
    }

    // Crear pública (sin login)
    @PostMapping("/publica")
    public ResponseEntity<?> crearPublica(@RequestBody IncidenciaRequest req) {
        try {
            Incidencia inc = incService.registrarIncidencia(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(inc);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Crear (con entidad completa)
    @PostMapping
    public ResponseEntity<Incidencia> crear(@RequestBody Incidencia inc) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incService.guardarIncidencia(inc));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizar(@PathVariable Long id, @RequestBody Incidencia inc) {
        Incidencia updated = incService.actualizarIncidencia(id, inc);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return incService.eliminarIncidencia(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
