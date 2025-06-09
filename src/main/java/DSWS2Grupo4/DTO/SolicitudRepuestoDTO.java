package DSWS2Grupo4.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SolicitudRepuestoDTO {
    private Long id;
    private Long idSolicitud;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private String nombreTecnico;
    private Long idIncidencia;
    
    private List<DetalleRepuestoDTO> detalles;
    
    private String codigoRepuesto;
    private String nombreRepuesto;
    private String descripcionRepuesto;
    private int cantidad;

    public Long getIdSolicitud() {
        return this.id;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.id = idSolicitud;
    }
}