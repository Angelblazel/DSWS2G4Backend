
package DSWS2Grupo4.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolucionRequest {
    private Long idIncidencia;
    private Long idSolucion;
    private String palabrasClave;
    private String modalidadAtencion; // "remoto" o "taller"
    private String estado;   // "solucionado", etc.
}
