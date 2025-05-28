package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.SolucionSubcategoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SolucionSubcategoriaRepository extends JpaRepository<SolucionSubcategoria, Long> {
    List<SolucionSubcategoria> findByProblema_Id(Long problemaId);
}
