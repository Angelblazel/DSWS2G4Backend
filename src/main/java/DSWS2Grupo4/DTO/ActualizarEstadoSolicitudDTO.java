package DSWS2Grupo4.DTO;

//import DSWS2Grupo4.model.EstadoSolicitudRepuesto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarEstadoSolicitudDTO {
    private String nuevoEstado; // "ACEPTADO" o "RECHAZADO"
}

