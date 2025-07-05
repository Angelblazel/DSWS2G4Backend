package DSWS2Grupo4.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteIncidenciaDTO {
    private Long idIncidencia;
    private String correoSolicitante;
    private String codigoEquipo;
    private String problema;
    private String estado;
    private String modalidadAtencion;
    private LocalDateTime fechaRegistro;
    private String tecnicoAsignado;
    private Integer prioridad;
    private String categoria;
    private String subcategoria;
}