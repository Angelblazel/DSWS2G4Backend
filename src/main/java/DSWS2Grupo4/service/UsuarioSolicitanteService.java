package DSWS2Grupo4.service;

import DSWS2Grupo4.model.DatosEmpleado;
import DSWS2Grupo4.model.Equipo;
import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.repository.DatosEmpleadoRepository;
import DSWS2Grupo4.repository.EquipoRepository;
import DSWS2Grupo4.repository.UsuarioSolicitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioSolicitanteService {

    @Autowired
    private UsuarioSolicitanteRepository usuarioSolicitanteRepo;
    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private DatosEmpleadoRepository datosEmpleadoRepo;


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

    // Crear nuevo con equipo asignado automáticamente
    public UsuarioSolicitante crear(UsuarioSolicitante usuarioSolicitante) {
        if (usuarioSolicitante.getCorreoNumero() == null || usuarioSolicitante.getCorreoNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        if (usuarioSolicitante.getDatosEmpleado() == null) {
            throw new IllegalArgumentException("Los datos del empleado son obligatorios");
        }

        // 1. Guardar primero los datos del empleado
        DatosEmpleado datosGuardado = datosEmpleadoRepo.save(usuarioSolicitante.getDatosEmpleado());

        // 2. Buscar equipo libre
        Equipo equipoLibre = equipoRepository.findEquipoNoAsignado()
                .orElseThrow(() -> new RuntimeException("No hay equipos disponibles"));

        // 3. Asignar ambos
        usuarioSolicitante.setDatosEmpleado(datosGuardado);
        usuarioSolicitante.setEquipo(equipoLibre);

        // 4. Guardar usuario
        return usuarioSolicitanteRepo.save(usuarioSolicitante);
    }

    public UsuarioSolicitante actualizar(Long id, UsuarioSolicitante nuevoUsuario) {
        UsuarioSolicitante usuarioExistente = obtenerPorId(id);

        // Actualizar correo si se proporciona
        if (nuevoUsuario.getCorreoNumero() != null && !nuevoUsuario.getCorreoNumero().isBlank()) {
            usuarioExistente.setCorreoNumero(nuevoUsuario.getCorreoNumero());
        }

        // Actualizar datos del empleado
        if (nuevoUsuario.getDatosEmpleado() != null) {
            DatosEmpleado nuevosDatos = nuevoUsuario.getDatosEmpleado();
            DatosEmpleado datosActuales = usuarioExistente.getDatosEmpleado();

            if (datosActuales == null) {
                DatosEmpleado nuevosGuardado = datosEmpleadoRepo.save(nuevosDatos);
                usuarioExistente.setDatosEmpleado(nuevosGuardado);
            } else {
                if (nuevosDatos.getNombre() != null) datosActuales.setNombre(nuevosDatos.getNombre());
                if (nuevosDatos.getApellido() != null) datosActuales.setApellido(nuevosDatos.getApellido());
                if (nuevosDatos.getDni() != null) datosActuales.setDni(nuevosDatos.getDni());
                if (nuevosDatos.getCelular() != null) datosActuales.setCelular(nuevosDatos.getCelular());
                datosEmpleadoRepo.save(datosActuales);
            }
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