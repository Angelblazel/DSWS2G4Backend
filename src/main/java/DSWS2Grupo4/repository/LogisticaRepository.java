// LogisticaRepository.java
package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Logistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaRepository extends JpaRepository<Logistica, Long> {}
