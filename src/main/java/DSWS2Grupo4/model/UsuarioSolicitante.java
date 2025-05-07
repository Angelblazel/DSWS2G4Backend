package DSWS2Grupo4.model;

import jakarta.persistence.*;

@Entity
@Table (name = "usuarios_solicitantes")

public class UsuarioSolicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_solicitante")
    private Long id;

    @Column(name = "correo_numero", nullable = false)
    private String correoNumero;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    public UsuarioSolicitante() {}

    public UsuarioSolicitante(String correoNumero, Equipo equipo) {
        this.correoNumero = correoNumero;
        this.equipo = equipo;
    }

    public Long getId() {
        return id;
    }

    public String getCorreoNumero() {
        return correoNumero;
    }

    public void setCorreoNumero(String correoNumero) {
        this.correoNumero = correoNumero;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
