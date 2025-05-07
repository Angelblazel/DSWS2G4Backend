package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.IncidenciaRequest;
import DSWS2Grupo4.model.Equipo;
import DSWS2Grupo4.model.Incidencia;
import DSWS2Grupo4.model.UsuarioSolicitante;
import DSWS2Grupo4.repository.EquipoRepository;
import DSWS2Grupo4.repository.IncidenciaRepository;
import DSWS2Grupo4.repository.UsuarioSolicitanteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncidenciaService {

    @Autowired private EquipoRepository equipoRepo;
    @Autowired private UsuarioSolicitanteRepository usuarioRepo;
    @Autowired private IncidenciaRepository incidenciaRepo;

    public List<Incidencia> listarIncidencias() {
        return incidenciaRepo.findAll();
    }

    @Transactional
    public Incidencia registrarIncidencia(IncidenciaRequest req) {
        Equipo equipo = equipoRepo.findByCodigoEquipo(req.getCodigoEquipo())
            .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        UsuarioSolicitante usuario = usuarioRepo
            .findByCorreoNumeroAndEquipo_IdEquipo(req.getCorreoNumero(), equipo.getIdEquipo())
            .orElseGet(() -> {
                UsuarioSolicitante u = new UsuarioSolicitante();
                u.setCorreoNumero(req.getCorreoNumero());
                u.setEquipo(equipo);
                return usuarioRepo.save(u);
            });

        Incidencia inc = new Incidencia();
        inc.setUsuarioSolicitante(usuario);
        inc.setDescripcion(req.getDescripcion());
        return incidenciaRepo.save(inc);
    }

    public Incidencia guardarIncidencia(Incidencia inc) {
        return incidenciaRepo.save(inc);
    }

    public Incidencia actualizarIncidencia(Long id, Incidencia inc) {
        if (!incidenciaRepo.existsById(id)) return null;
        inc.setId(id);
        return incidenciaRepo.save(inc);
    }

    public boolean eliminarIncidencia(Long id) {
        if (!incidenciaRepo.existsById(id)) return false;
        incidenciaRepo.deleteById(id);
        return true;
    }

    public Incidencia obtenerPorId(Long id) {
        return incidenciaRepo.findById(id).orElse(null);
    }
}
