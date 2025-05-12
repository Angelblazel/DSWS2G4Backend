package DSWS2Grupo4.repository;

import DSWS2Grupo4.DTO.SubcategoriaDTO;
import DSWS2Grupo4.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
    @Query("SELECT new DSWS2Grupo4.DTO.SubcategoriaDTO(s.id, s.nombreSubcategoria, s.categoria.id) " +
            "FROM Subcategoria s WHERE s.categoria.id = :categoriaId")
    List<SubcategoriaDTO> findByCategoriaId(@Param("categoriaId") Long categoriaId);
}
