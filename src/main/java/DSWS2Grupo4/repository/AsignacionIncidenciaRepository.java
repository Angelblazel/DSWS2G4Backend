package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.AsignacionIncidencia;
import DSWS2Grupo4.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsignacionIncidenciaRepository extends JpaRepository<AsignacionIncidencia, Long> {
    boolean existsByIncidenciaId(Long idIncidencia);
    Optional<AsignacionIncidencia> findByIncidencia(Incidencia incidencia);
}
