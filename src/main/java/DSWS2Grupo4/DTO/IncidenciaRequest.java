package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class IncidenciaRequest {
    private String correoNumero;
    private String codigoEquipo;
    private String descripcion;
}
