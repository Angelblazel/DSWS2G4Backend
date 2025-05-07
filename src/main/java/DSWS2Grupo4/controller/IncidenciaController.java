package DSWS2Grupo4.controller;

import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @Autowired
    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public List<Incidencia> listarIncidencias() {
        return incidenciaService.listarIncidencias();
    }

    @PostMapping
    public Incidencia crearIncidencia(@RequestBody Incidencia incidencia) {
        return incidenciaService.guardarIncidencia(incidencia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtenerIncidenciaPorId(@PathVariable Long id) {
        return incidenciaService.obtenerIncidenciaPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizarIncidencia(@PathVariable Long id, @RequestBody Incidencia incidencia) {
        Incidencia updatedIncidencia = incidenciaService.actualizarIncidencia(id, incidencia);
        return updatedIncidencia != null ? ResponseEntity.ok(updatedIncidencia) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidencia(@PathVariable Long id) {
        return incidenciaService.eliminarIncidencia(id) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

