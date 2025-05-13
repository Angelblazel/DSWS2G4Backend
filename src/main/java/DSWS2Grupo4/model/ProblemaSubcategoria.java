package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "problema_subcategoria")
@Getter
@Setter
@NoArgsConstructor
public class ProblemaSubcategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_problema")
    private Long id;

    @Column(name = "descripcion_problema")
    private String descripcionProblema;

    @Column(name = "prioridad_problema")
    private Integer prioridadProblema;

    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;
}
