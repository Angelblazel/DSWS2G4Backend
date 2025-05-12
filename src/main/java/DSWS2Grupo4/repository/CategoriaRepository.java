package DSWS2Grupo4.repository;

import DSWS2Grupo4.DTO.CategoriaDTO;
import DSWS2Grupo4.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT new DSWS2Grupo4.DTO.CategoriaDTO(c.id, c.nombreCategoria) FROM Categoria c")
    List<CategoriaDTO> findAllCategorias();
}
