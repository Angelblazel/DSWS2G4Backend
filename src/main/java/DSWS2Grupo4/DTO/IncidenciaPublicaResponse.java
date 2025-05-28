package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class IncidenciaPublicaResponse {
    private Long idIncidencia;
    private String estado;
    private LocalDateTime fechaRegistro;
    private String correoSolicitante;
    private String categoriaProblema;
    private String subCategoria;
    private String descripcionProblema;
    private String prioridad;
    private String codigoEquipo;

    // Getters y setters
}

