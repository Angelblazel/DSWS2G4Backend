package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.DetalleSolicitudRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSolicitudRepuestoRepository extends JpaRepository<DetalleSolicitudRepuesto, Long> {
}

