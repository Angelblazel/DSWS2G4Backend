package DSWS2Grupo4.DTO;

import DSWS2Grupo4.model.EstadoIncidencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaTecnicoDTO {
    private Long idIncidencia;
    private String correoSolicitante;
    private String codigoEquipo;
    private LocalDateTime fechaRegistro;
    private String descripcionProblema;
    private EstadoIncidencia estado;
    private Integer prioridad;
}