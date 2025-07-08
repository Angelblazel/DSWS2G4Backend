package DSWS2Grupo4.service;

import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.repository.UsuarioSolicitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioSolicitanteService {

    @Autowired
    private UsuarioSolicitanteRepository usuarioSolicitanteRepo;

    // Listar todos
    public List<UsuarioSolicitante> listarTodos() {
        return usuarioSolicitanteRepo.findAll();
    }

    // Buscar por ID
    public UsuarioSolicitante obtenerPorId(Long id) {
        return usuarioSolicitanteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioSolicitante no encontrado con ID: " + id));
    }

    // Nuevo método para buscar por correo
    public UsuarioSolicitante buscarPorCorreo(String correo) {
        return usuarioSolicitanteRepo.findByCorreoNumero(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }

    // Crear nuevo
    public UsuarioSolicitante crear(UsuarioSolicitante usuarioSolicitante) {
        if (usuarioSolicitante.getCorreoNumero() == null || usuarioSolicitante.getCorreoNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        if (usuarioSolicitante.getEquipo() == null) {
            throw new IllegalArgumentException("El equipo es obligatorio");
        }

        return usuarioSolicitanteRepo.save(usuarioSolicitante);
    }

    // Actualizar
    public UsuarioSolicitante actualizar(Long id, UsuarioSolicitante nuevoUsuario) {
        UsuarioSolicitante usuarioExistente = obtenerPorId(id);

        if (nuevoUsuario.getCorreoNumero() != null) {
            usuarioExistente.setCorreoNumero(nuevoUsuario.getCorreoNumero());
        }

        if (nuevoUsuario.getPrioridadUsuario() != null) {
            usuarioExistente.setPrioridadUsuario(nuevoUsuario.getPrioridadUsuario());
        }

        if (nuevoUsuario.getEquipo() != null) {
            usuarioExistente.setEquipo(nuevoUsuario.getEquipo());
        }

        return usuarioSolicitanteRepo.save(usuarioExistente);
    }

    // Eliminar
    public void eliminar(Long id) {
        if (!usuarioSolicitanteRepo.existsById(id)) {
            throw new RuntimeException("UsuarioSolicitante no encontrado con ID: " + id);
        }
        usuarioSolicitanteRepo.deleteById(id);
    }
}