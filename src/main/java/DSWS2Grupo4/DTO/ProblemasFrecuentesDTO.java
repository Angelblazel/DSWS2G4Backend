package DSWS2Grupo4.DTO;

public class ProblemasFrecuentesDTO {
    private String nombreProblema;
    private Long cantidad;

    public ProblemasFrecuentesDTO(String nombreProblema, Long cantidad) {
        this.nombreProblema = nombreProblema;
        this.cantidad = cantidad;
    }

    public String getNombreProblema() {
        return nombreProblema;
    }

    public void setNombreProblema(String nombreProblema) {
        this.nombreProblema = nombreProblema;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
