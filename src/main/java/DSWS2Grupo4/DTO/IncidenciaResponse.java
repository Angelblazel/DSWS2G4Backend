package DSWS2Grupo4.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IncidenciaResponse {
    private Long idIncidencia;
    private Integer prioridad;
    private String mensaje;
}