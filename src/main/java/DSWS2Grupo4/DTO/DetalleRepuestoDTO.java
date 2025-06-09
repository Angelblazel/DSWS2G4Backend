package DSWS2Grupo4.DTO;

import lombok.Data;

@Data
public class DetalleRepuestoDTO {
    private String codigoRepuesto;
    private String nombreRepuesto;
    private String descripcionRepuesto;
    private int cantidad;
}