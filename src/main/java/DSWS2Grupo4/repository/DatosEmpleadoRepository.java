package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.DatosEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosEmpleadoRepository extends JpaRepository<DatosEmpleado, Long> {
}
