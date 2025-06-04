package DSWS2Grupo4.controller;

import DSWS2Grupo4.model.Repuesto;
import DSWS2Grupo4.repository.RepuestoRepository;
import DSWS2Grupo4.service.RepuestoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repuesto")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;
    @Autowired
    private RepuestoRepository repuestoRepository;

    @GetMapping
    public ResponseEntity<List<Repuesto>> listar(){
        return ResponseEntity.ok(repuestoService.listaRepuestos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(repuestoService.obtenerRepuestoPorId(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Repuesto> obtenerPorCodigo(@PathVariable String codigo){
        try {
            return ResponseEntity.ok(repuestoService.obtenerRepuestoPorCodigo(codigo));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

     @PostMapping
     public ResponseEntity<Repuesto> guardarRepuesto(@RequestBody Repuesto repuesto){
        return ResponseEntity.ok(repuestoService.registrarRepuesto(repuesto));
     }
     @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizarRepuesto(@PathVariable Long id, @RequestBody Repuesto repuesto){
        try{
            return ResponseEntity.ok(repuestoService.actualizarRepuesto(id, repuesto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
     }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return repuestoService.eliminarRepuesto(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
