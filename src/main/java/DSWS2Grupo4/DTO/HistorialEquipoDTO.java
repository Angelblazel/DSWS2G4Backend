package DSWS2Grupo4.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HistorialEquipoDTO {
    private Long id;
    private String codigoEquipo;
    private String descripcionProblema;
    private String nombreCategoria;
    private String nombreSubcategoria;
    private String solucionProblema;
    private String palabrasClave;
    private LocalDateTime fechaSolucion;
}
