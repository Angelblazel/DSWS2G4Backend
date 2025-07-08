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

    // Crear nuevo
    public UsuarioSolicitante crear(UsuarioSolicitante usuarioSolicitante) {
        return usuarioSolicitanteRepo.save(usuarioSolicitante);
    }

    // Actualizar
    public UsuarioSolicitante actualizar(Long id, UsuarioSolicitante nuevoUsuario) {
        UsuarioSolicitante existente = obtenerPorId(id);
        existente.setCorreoNumero(nuevoUsuario.getCorreoNumero());
        existente.setPrioridadUsuario(nuevoUsuario.getPrioridadUsuario());
        existente.setEquipo(nuevoUsuario.getEquipo());
        return usuarioSolicitanteRepo.save(existente);
    }

    // Eliminar
    public void eliminar(Long id) {
        if (!usuarioSolicitanteRepo.existsById(id)) {
            throw new RuntimeException("UsuarioSolicitante no encontrado con ID: " + id);
        }
        usuarioSolicitanteRepo.deleteById(id);
    }
}