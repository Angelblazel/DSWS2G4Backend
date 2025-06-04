package DSWS2Grupo4.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudRepuestoDTO {
    private Long id;
    private String codigoRepuesto;
    private String nombreRepuesto;
    private int cantidad;
    private String estado;
    private LocalDateTime fechaSolicitud;
}

