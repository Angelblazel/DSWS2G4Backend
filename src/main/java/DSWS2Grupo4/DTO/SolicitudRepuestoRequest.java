package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SolicitudRepuestoRequest {
    private Long idIncidencia;         // ID de la incidencia asociada
    private Long idTecnico;            // ID del técnico que solicita
    private List<DetalleSolicitudDTO> detalles; // Lista de repuestos solicitados
}
