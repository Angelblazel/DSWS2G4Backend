package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.CodigoEquipoRequest;
import DSWS2Grupo4.DTO.PalabraClaveRequest;
import DSWS2Grupo4.dto.HistorialEquipoDTO;
import DSWS2Grupo4.service.HistorialEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*") // O especifica tu frontend si gustas
public class HistorialEquipoController {

    @Autowired
    private HistorialEquipoService historialEquipoService;

    @PostMapping("/buscar")
    public ResponseEntity<List<HistorialEquipoDTO>> buscarPorPalabraClave(@RequestBody PalabraClaveRequest request) {
        List<HistorialEquipoDTO> resultados = historialEquipoService.buscarPorPalabraClave(request.getPalabra());

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultados);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<HistorialEquipoDTO>> obtenerTodo() {
        List<HistorialEquipoDTO> historial = historialEquipoService.obtenerTodoElHistorial();
        if(historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }
    
    @PostMapping("/buscar-por-codigo")
    public ResponseEntity<List<HistorialEquipoDTO>> buscarPorCodigoEquipoJSON(
            @RequestBody CodigoEquipoRequest request) {
        List<HistorialEquipoDTO> resultados = historialEquipoService.buscarPorCodigoEquipo(request.getCodigo());
        return ResponseEntity.ok(resultados);
    }
}
