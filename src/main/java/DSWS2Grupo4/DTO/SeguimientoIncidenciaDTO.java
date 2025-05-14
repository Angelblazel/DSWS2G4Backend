package DSWS2Grupo4.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientoIncidenciaDTO {
    private Long idIncidencia;
    private String estado;
    private String modalidadAtencion;
    private String fechaRegistro;
    private String problema;
    private String tecnicoAsignado;     // Puede ser null si no hay asignación
    private String historialSolucion;   // Puede ser null si aún no hay solución
}
