package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    @Query("SELECT i FROM Incidencia i JOIN i.asignacion a WHERE a.tecnico.empleado.idEmpleado = :tecnicoId")
    List<Incidencia> findByTecnicoId(@Param("tecnicoId") Integer tecnicoId);

    @Query("SELECT i FROM Incidencia i JOIN i.asignacion a WHERE a.tecnico.empleado.idEmpleado = :tecnicoId AND i.id = :ticket")
    List<Incidencia> findByTecnicoIdAndTicket(
            @Param("tecnicoId") Integer tecnicoId,
            @Param("ticket") Long ticket
    );

    List<Incidencia> findByUsuarioSolicitante_CorreoNumero(String correo);
    List<Incidencia> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Incidencia> findByFechaGreaterThanEqual(LocalDateTime fechaInicio);
    List<Incidencia> findByFechaLessThanEqual(LocalDateTime fechaFin);
}
