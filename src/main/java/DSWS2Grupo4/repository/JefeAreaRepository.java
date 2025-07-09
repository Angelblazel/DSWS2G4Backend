package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.JefeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JefeAreaRepository extends JpaRepository<JefeArea, Long> {
}
