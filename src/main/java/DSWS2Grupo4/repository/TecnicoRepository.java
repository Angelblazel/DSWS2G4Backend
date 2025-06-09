package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    // Buscar todos los técnicos cuya carga actual sea menor a la carga máxima
    @Query("SELECT t FROM Tecnico t WHERE t.cargaActual < t.cargaMaxima")
    List<Tecnico> findTecnicosDisponibles();
    
    // Buscar técnico por ID de empleado
    @Query("SELECT t FROM Tecnico t WHERE t.empleado.idEmpleado = :idEmpleado")
    Optional<Tecnico> findByEmpleadoId(@Param("idEmpleado") Integer idEmpleado);
}
