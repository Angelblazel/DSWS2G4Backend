package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class IncidenciaPublicaResponse {
    private Long id;
    private String estado;
    private LocalDateTime fechaRegistro;
    private String correoSolicitante;
    private String descripcionProblema;

    // Getters y setters
}

