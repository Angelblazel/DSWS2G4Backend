package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SubcategoriaDTO {
    private Long id;
    private String nombreSubcategoria;
    private Long categoriaId;

    public SubcategoriaDTO(Long id, String nombreSubcategoria, Long categoriaId) {
        this.id = id;
        this.nombreSubcategoria = nombreSubcategoria;
        this.categoriaId = categoriaId;
    }
}
