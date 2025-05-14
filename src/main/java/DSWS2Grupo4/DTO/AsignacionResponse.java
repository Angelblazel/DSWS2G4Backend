package DSWS2Grupo4.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionResponse {
    private Long idTecnico;
    private String nombreTecnico;
    private int cargaActual;
    private int cargaMaxima;

    private Long idIncidencia;
    private String estadoIncidencia;
    private LocalDateTime fechaAsignacion;

    private String mensaje;

}
