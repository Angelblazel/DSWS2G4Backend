package DSWS2Grupo4.repository;

import DSWS2Grupo4.DTO.ProblemaDTO;
import DSWS2Grupo4.model.ProblemaSubcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemaSubcategoriaRepository extends JpaRepository<ProblemaSubcategoria, Long> {
    @Query("SELECT new DSWS2Grupo4.DTO.ProblemaDTO(p.id, p.descripcionProblema, p.subcategoria.id) " +
            "FROM ProblemaSubcategoria p WHERE p.subcategoria.id = :subcategoriaId")
    List<ProblemaDTO> findBySubcategoriaId(@Param("subcategoriaId") Long subcategoriaId);
}
