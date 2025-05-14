package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.AsignacionResponse;
import DSWS2Grupo4.model.AsignacionIncidencia;
import DSWS2Grupo4.model.EstadoIncidencia;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.model.Tecnico;
import DSWS2Grupo4.repository.AsignacionIncidenciaRepository;
import DSWS2Grupo4.repository.IncidenciaRepository;
import DSWS2Grupo4.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsignacionIncidenciaService {
    @Autowired
    private AsignacionIncidenciaRepository asignacionRepo;

    @Autowired
    private TecnicoRepository tecnicoRepo;

    @Autowired
    private IncidenciaRepository incidenciaRepo;

    //Verificacion de Incidencias sin asignar
    public List<Incidencia> obtenerIncidenciasNoAsignadas() {
        List<Incidencia> todas = incidenciaRepo.findAll();
        return todas.stream()
                .filter(inc -> !asignacionRepo.existsByIncidenciaId(inc.getId()))
                .toList();
    }

    //Asigna un técnico a una incidencia validando carga y estado
    public void asignarTecnico(Long idIncidencia, Long idTecnico) {
        // Validar si ya fue asignada
        if (asignacionRepo.existsByIncidenciaId(idIncidencia)) {
            throw new IllegalStateException("La incidencia ya tiene un técnico asignado.");
        }

        // Buscar técnico
        Tecnico tecnico = tecnicoRepo.findById(idTecnico)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));

        // Verificar que tenga capacidad
        if (tecnico.getCargaActual() >= tecnico.getCargaMaxima()) {
            throw new IllegalStateException("Técnico ha alcanzado la carga máxima.");
        }

        // Buscar incidencia
        Incidencia incidencia = incidenciaRepo.findById(idIncidencia)
                .orElseThrow(() -> new IllegalArgumentException("Incidencia no encontrada"));

        // Cambiar estado de la incidencia
        incidencia.setEstado(EstadoIncidencia.en_proceso);
        incidenciaRepo.save(incidencia);

        // Actualizar carga del técnico
        tecnico.setCargaActual(tecnico.getCargaActual() + 1);
        tecnicoRepo.save(tecnico);

        // Registrar asignación
        AsignacionIncidencia asignacion = new AsignacionIncidencia();
        asignacion.setTecnico(tecnico);
        asignacion.setIncidencia(incidencia);

        asignacionRepo.save(asignacion);
    }

    //Obtiene los técnicos con capacidad disponible
    public List<Tecnico> obtenerTecnicosDisponibles() {
        return tecnicoRepo.findAll().stream()
                .filter(t -> t.getCargaActual() < t.getCargaMaxima())
                .toList();
    }

    public AsignacionResponse asignarIncidenciaYDevolverInfo(Long idTecnico, Long idIncidencia) {
        Tecnico tecnico = tecnicoRepo.findById(idTecnico)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        Incidencia incidencia = incidenciaRepo.findById(idIncidencia)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        if (asignacionRepo.findByIncidencia(incidencia).isPresent()) {
            throw new RuntimeException("La incidencia ya está asignada.");
        }

        if (tecnico.getCargaActual() >= tecnico.getCargaMaxima()) {
            throw new RuntimeException("El técnico ya tiene su carga máxima.");
        }

        AsignacionIncidencia asignacion = new AsignacionIncidencia();
        asignacion.setTecnico(tecnico);
        asignacion.setIncidencia(incidencia);
        asignacion.setFechaAsignacion(LocalDateTime.now());
        asignacionRepo.save(asignacion);

        tecnico.setCargaActual(tecnico.getCargaActual() + 1);
        tecnicoRepo.save(tecnico);

        incidencia.setEstado(EstadoIncidencia.en_proceso);
        incidenciaRepo.save(incidencia);

        AsignacionResponse response = new AsignacionResponse();
        response.setIdTecnico(tecnico.getId());
        response.setNombreTecnico(tecnico.getEmpleado().getNombre());
        response.setCargaActual(tecnico.getCargaActual());
        response.setCargaMaxima(tecnico.getCargaMaxima());
        response.setIdIncidencia(incidencia.getId());
        response.setEstadoIncidencia(incidencia.getEstado().toString());
        response.setFechaAsignacion(asignacion.getFechaAsignacion());
        response.setMensaje("Incidencia asignada correctamente.");

        return response;
    }
}
