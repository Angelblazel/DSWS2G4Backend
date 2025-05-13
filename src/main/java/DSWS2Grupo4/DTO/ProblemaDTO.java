package DSWS2Grupo4.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ProblemaDTO {
    private Long id;
    private String descripcionProblema;
    private Long subcategoriaId;

    public ProblemaDTO(Long id, String descripcionProblema, Long subcategoriaId) {
        this.id = id;
        this.descripcionProblema = descripcionProblema;
        this.subcategoriaId = subcategoriaId;
    }
}