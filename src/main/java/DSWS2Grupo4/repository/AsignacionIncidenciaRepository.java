package DSWS2Grupo4.repository;

import DSWS2Grupo4.DTO.ProblemasFrecuentesDTO;
import DSWS2Grupo4.DTO.TicketsPorTecnicoDTO;
import DSWS2Grupo4.model.AsignacionIncidencia;
import DSWS2Grupo4.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionIncidenciaRepository extends JpaRepository<AsignacionIncidencia, Long> {
    boolean existsByIncidenciaId(Long idIncidencia);
    Optional<AsignacionIncidencia> findByIncidencia(Incidencia incidencia);

    @Query("SELECT new DSWS2Grupo4.DTO.TicketsPorTecnicoDTO(t.nombre, COUNT(a)) " +
       "FROM AsignacionIncidencia a " +
       "JOIN a.tecnico t " +
       "JOIN a.incidencia i " +
       "WHERE i.estado = 'solucionado' " +
       "GROUP BY t.nombre")
    List<TicketsPorTecnicoDTO> contarTicketsAtendidosPorTecnico();

    @Query("SELECT new DSWS2Grupo4.DTO.ProblemasFrecuentesDTO(p.descripcionProblema, COUNT(i)) " +
       "FROM Incidencia i " +
       "JOIN i.problemaSubcategoria p " +
       "GROUP BY p.descripcionProblema " +
       "ORDER BY COUNT(i) DESC")
    List<ProblemasFrecuentesDTO> obtenerProblemasMasFrecuentes();
}
