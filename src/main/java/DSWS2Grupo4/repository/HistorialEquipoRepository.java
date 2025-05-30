package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.HistorialEquipo;
import DSWS2Grupo4.model.Incidencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface HistorialEquipoRepository extends JpaRepository<HistorialEquipo, Long> {

    // Obtener historial por incidencia
    Optional<HistorialEquipo> findByIncidencia(Incidencia incidencia);

    // Historial por ID de incidencia
    Optional<HistorialEquipo> findByIncidencia_Id(Long idIncidencia);
    
    @Query("SELECT h FROM HistorialEquipo h WHERE LOWER(h.palabrasClave) LIKE LOWER(CONCAT('%', :palabraClave, '%'))")
    List<HistorialEquipo> buscarPorPalabraClave(String palabraClave);
    
    @Query("SELECT h FROM HistorialEquipo h " +
       "WHERE LOWER(h.incidencia.usuarioSolicitante.equipo.codigoEquipo) = LOWER(:codigo)")
    List<HistorialEquipo> buscarPorCodigoEquipo(@Param("codigo") String codigo);

}