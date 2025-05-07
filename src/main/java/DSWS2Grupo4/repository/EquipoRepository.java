package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByCodigoEquipo(String codigoEquipo);
}
