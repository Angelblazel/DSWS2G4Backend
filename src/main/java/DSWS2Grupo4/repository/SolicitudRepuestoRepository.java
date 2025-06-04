package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.EstadoSolicitudRepuesto;
import DSWS2Grupo4.model.SolicitudRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepuestoRepository extends JpaRepository<SolicitudRepuesto, Long> {
    List<SolicitudRepuesto> findByEstado(String estado);
}