package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_categorias")
@Getter
@Setter
@NoArgsConstructor
public class Subcategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Long id;

    @Column(name = "nombre_subcategoria")
    private String nombreSubcategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
