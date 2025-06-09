package DSWS2Grupo4.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepuestoDTO {
    private Long id;
    private String codigoRepuesto;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
}