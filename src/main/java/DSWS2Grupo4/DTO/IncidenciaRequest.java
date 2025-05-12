package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class IncidenciaRequest {
    private String correo;
    private String codigoEquipo;
    private Long categoriaId;
    private Long subcategoriaId;
    private Long problemaId;
}
