package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.AlertaIncidencia;
import DSWS2Grupo4.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaIncidenciaRepository extends JpaRepository<AlertaIncidencia, Long> {
    boolean existsByIncidencia(Incidencia incidencia);
    List<AlertaIncidencia> findByIncidencia_Id(Long idIncidencia);
}

