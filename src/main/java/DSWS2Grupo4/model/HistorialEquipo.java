package DSWS2Grupo4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_equipos")
@Getter
@Setter
@NoArgsConstructor
public class HistorialEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_incidencia", unique = true)
    @JsonBackReference
    private Incidencia incidencia;

    @ManyToOne
    @JsonIgnore 
    @JoinColumn(name = "id_solucion_problema")
    private SolucionSubcategoria solucion;

    @Column(name = "palabras_clave")
    private String palabrasClave;

    @Column(name = "fecha_solucion")
    private LocalDateTime fechaSolucion;
}

