package DSWS2Grupo4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtencionIncidenciaDTO {
    private Long idIncidencia;
    private String palabraClave;
    private Long idSolucionSubcategoria; // id de SolucionSubcategoria (ManyToOne)
    private String modalidad; // "remoto" o "presencial"
}
