package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.AsignacionRequest;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.model.Tecnico;
import DSWS2Grupo4.service.AsignacionIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignacion")
public class AsignacionIncidenciaController {

    @Autowired
    private AsignacionIncidenciaService asignacionService;

    @GetMapping("/no-asignadas")
    public ResponseEntity<List<Incidencia>> obtenerIncidenciasNoAsignadas() {
        List<Incidencia> noAsignadas = asignacionService.obtenerIncidenciasNoAsignadas();
        return ResponseEntity.ok(noAsignadas);
    }

    @PostMapping
    public ResponseEntity<?> asignarTecnico(@RequestBody AsignacionRequest request){
        try {
            asignacionService.asignarTecnico(request.getIdIncidencia(), request.getIdTecnico());
            return ResponseEntity.ok("Técnico asignado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/tecnicos-disponibles")
    public ResponseEntity<List<Tecnico>> obtenerTecnicosDisponibles() {
        List<Tecnico> tecnicos = asignacionService.obtenerTecnicosDisponibles();
        return ResponseEntity.ok(tecnicos);
    }
}
