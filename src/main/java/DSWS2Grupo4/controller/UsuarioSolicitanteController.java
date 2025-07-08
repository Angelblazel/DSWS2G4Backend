package DSWS2Grupo4.controller;

import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.service.UsuarioSolicitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-solicitantes")
@CrossOrigin(origins = "*")
public class UsuarioSolicitanteController {

    @Autowired
    private UsuarioSolicitanteService usuarioSolicitanteService;

    @GetMapping
    public List<UsuarioSolicitante> listarTodos() {
        return usuarioSolicitanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioSolicitante obtenerPorId(@PathVariable Long id) {
        return usuarioSolicitanteService.obtenerPorId(id);
    }

    @PostMapping
    public UsuarioSolicitante crear(@RequestBody UsuarioSolicitante usuario) {
        return usuarioSolicitanteService.crear(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioSolicitante actualizar(@PathVariable Long id, @RequestBody UsuarioSolicitante usuarioActualizado) {
        return usuarioSolicitanteService.actualizar(id, usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioSolicitanteService.eliminar(id);
    }
}