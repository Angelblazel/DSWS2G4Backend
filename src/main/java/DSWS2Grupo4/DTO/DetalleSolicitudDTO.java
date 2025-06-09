package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleSolicitudDTO {
    private Long idRepuesto; // ID del repuesto solicitado
    private String nombreRepuesto; // Nombre del repuesto
    private Integer cantidad;    // Cantidad solicitada
    private String descripcion; // Descripción del repuesto
}
