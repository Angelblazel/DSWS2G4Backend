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
        return usuarioSolicitanteRepo.save(usuarioSolicitante);
    }

    // Actualizar
    public UsuarioSolicitante actualizar(Long id, UsuarioSolicitante nuevoUsuario) {
        UsuarioSolicitante usuarioExistente = obtenerPorId(id);
        usuarioExistente.setCorreoNumero(nuevoUsuario.getCorreoNumero());
        usuarioExistente.setPrioridadUsuario(nuevoUsuario.getPrioridadUsuario());
        usuarioExistente.setEquipo(nuevoUsuario.getEquipo());
        return usuarioSolicitanteRepo.save(usuarioExistente);
    }

    // Eliminar
    public void eliminar(Long id) {
        usuarioSolicitanteRepo.deleteById(id);
    }
}