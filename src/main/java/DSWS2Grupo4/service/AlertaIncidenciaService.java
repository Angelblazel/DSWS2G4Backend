package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.AlertaRequest;
import DSWS2Grupo4.model.AlertaIncidencia;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.repository.AlertaIncidenciaRepository;
import DSWS2Grupo4.repository.IncidenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertaIncidenciaService {

    @Autowired
    private AlertaIncidenciaRepository alertaRepo;

    @Autowired
    private IncidenciaRepository incidenciaRepo;

    public void registrarAlerta(AlertaRequest req) {
        Incidencia incidencia = incidenciaRepo.findById(req.getIdIncidencia())
                .orElseThrow(() -> new EntityNotFoundException("Incidencia no encontrada"));

        // Validar estado
        if (incidencia.getEstado() == DSWS2Grupo4.model.EstadoIncidencia.solucionado) {
            throw new IllegalStateException("La incidencia ya fue solucionada. No se puede generar una alerta.");
        }

        // Validar tiempo transcurrido (ej. más de 48 horas desde el registro)
        LocalDateTime fechaRegistro = incidencia.getFecha();
        if (fechaRegistro.plusHours(48).isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Aún no ha pasado el tiempo suficiente para generar una alerta.");
        }

        // Verificar si ya existe una alerta previa para esta incidencia
        boolean existeAlerta = alertaRepo.existsByIncidencia(incidencia);
        if (existeAlerta) {
            throw new IllegalStateException("Ya existe una alerta registrada para esta incidencia.");
        }

        // Registrar alerta
        AlertaIncidencia alerta = new AlertaIncidencia();
        alerta.setIncidencia(incidencia);
        alerta.setMotivo(req.getMotivo());
        alerta.setFechaAlerta(LocalDateTime.now());

        alertaRepo.save(alerta);
    }

}

