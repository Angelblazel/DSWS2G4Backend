package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByCodigoEquipo(String codigoEquipo);

    @Query(value = """
        SELECT e.* FROM equipos e
        WHERE e.id_equipo NOT IN (
            SELECT COALESCE(us.id_equipo, 0)
            FROM usuarios_solicitantes us
            WHERE us.id_equipo IS NOT NULL
        )
        LIMIT 1
    """, nativeQuery = true)
    Optional<Equipo> findEquipoNoAsignado();
}
