package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jefe_area")
@Getter
@Setter
@NoArgsConstructor
public class JefeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jefe_area")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_empleado", nullable = false, unique = true)
    private Usuario empleado;
}
