package DSWS2Grupo4.service;

import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    @Autowired
    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    public List<Incidencia> listarIncidencias() {
        return incidenciaRepository.findAll();
    }

    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public Optional<Incidencia> obtenerIncidenciaPorId(Long id) {
        return incidenciaRepository.findById(id);
    }

    public Incidencia actualizarIncidencia(Long id, Incidencia incidencia) {
        if (incidenciaRepository.existsById(id)) {
            incidencia.setId(id);
            return incidenciaRepository.save(incidencia);
        }
        return null;  // O lanzar una excepción según el caso
    }

    public boolean eliminarIncidencia(Long id) {
        if (incidenciaRepository.existsById(id)) {
            incidenciaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


