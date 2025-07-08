package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.IncidenciaTecnicoDTO;
import DSWS2Grupo4.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnico")
@CrossOrigin(origins = "*")
public class TecnicoController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping("/incidencias/{tecnicoId}")
    @PreAuthorize("hasAuthority('TECNICO')")
    public ResponseEntity<List<IncidenciaTecnicoDTO>> listarIncidenciasTecnico(
            @PathVariable Integer tecnicoId,
            @RequestParam(required = false) String numeroTicket) {

        return ResponseEntity.ok(incidenciaService.listarIncidenciasTecnico(tecnicoId, numeroTicket));
    }

    // Endpoint simple para probar la autenticación del técnico
    @GetMapping("/test")
    public ResponseEntity<String> testTecnicoAccess() {
        return ResponseEntity.ok("Acceso permitido para técnico");
    }
}