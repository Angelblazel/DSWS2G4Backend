package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    Optional<Repuesto> findByCodigoRepuesto(String codigoRepuesto);
    List<Repuesto> findByNombreContainingIgnoreCase(String nombre);
    List<Repuesto> findByCantidadGreaterThan(Integer cantidad);
}
