package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleSolicitudDTO {
    private Long idRepuesto; // ID del repuesto solicitado
    private int cantidad;    // Cantidad solicitada
}
