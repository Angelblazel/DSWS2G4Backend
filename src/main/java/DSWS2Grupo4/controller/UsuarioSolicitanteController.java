package DSWS2Grupo4.controller;

import DSWS2Grupo4.dto.UsuarioEquipoResponse;
import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.service.UsuarioSolicitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios-solicitantes")
@CrossOrigin(origins = "*")
public class UsuarioSolicitanteController {

    @Autowired
    private UsuarioSolicitanteService usuarioSolicitanteService;

    @GetMapping
    public List<UsuarioSolicitante> listarTodos() {
        return usuarioSolicitanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSolicitante> obtenerPorId(@PathVariable Long id) {
        try {
            UsuarioSolicitante usuario = usuarioSolicitanteService.obtenerPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para buscar por correo (público)
    @GetMapping("/buscar-por-correo")
    public ResponseEntity<?> buscarPorCorreo(@RequestParam String correo) {
        try {
            UsuarioSolicitante usuario = usuarioSolicitanteService.buscarPorCorreo(correo);

            if (usuario.getEquipo() == null) {
                return ResponseEntity.badRequest().body("El usuario no tiene un equipo asignado.");
            }

            UsuarioEquipoResponse response = new UsuarioEquipoResponse(
                    usuario.getCorreoNumero(),
                    usuario.getEquipo().getCodigoEquipo()
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioSolicitante usuario) {
        try {
            UsuarioSolicitante usuarioCreado = usuarioSolicitanteService.crear(usuario);
            return ResponseEntity.ok(usuarioCreado);
        } catch (Exception e) {
            e.printStackTrace(); // también verás el error en consola
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSolicitante> actualizar(@PathVariable Long id, @RequestBody UsuarioSolicitante usuarioActualizado) {
        try {
            UsuarioSolicitante usuario = usuarioSolicitanteService.actualizar(id, usuarioActualizado);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioSolicitanteService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}