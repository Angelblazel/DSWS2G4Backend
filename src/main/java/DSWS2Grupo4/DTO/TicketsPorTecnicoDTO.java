package DSWS2Grupo4.DTO;

public class TicketsPorTecnicoDTO {
    private String nombreTecnico;
    private Long totalTickets;

    public TicketsPorTecnicoDTO(String nombreTecnico, Long totalTickets) {
        this.nombreTecnico = nombreTecnico;
        this.totalTickets = totalTickets;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public Long getTotalTickets() {
        return totalTickets;
    }
}
