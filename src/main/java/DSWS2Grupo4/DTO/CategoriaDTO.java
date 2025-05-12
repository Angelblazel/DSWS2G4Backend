package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombreCategoria;

    public CategoriaDTO(Long id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }
}