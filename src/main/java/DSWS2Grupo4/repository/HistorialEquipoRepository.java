package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.HistorialEquipo;
import DSWS2Grupo4.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialEquipoRepository extends JpaRepository<HistorialEquipo, Long> {

    // Obtener historial por incidencia
    Optional<HistorialEquipo> findByIncidencia(Incidencia incidencia);

    // Historial por ID de incidencia
    Optional<HistorialEquipo> findByIncidencia_Id(Long idIncidencia);
}

