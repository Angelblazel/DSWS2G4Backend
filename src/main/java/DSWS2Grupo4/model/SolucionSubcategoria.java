package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "solucion_subcategoria")
@Getter
@Setter
@NoArgsConstructor
public class SolucionSubcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solucion_problema")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_problema", nullable = false)
    private ProblemaSubcategoria problema;

    @Column(name = "solucion_problema", nullable = false)
    private String solucionProblema;

    @OneToMany(mappedBy = "solucion")
    private List<HistorialEquipo> historiales;
}
