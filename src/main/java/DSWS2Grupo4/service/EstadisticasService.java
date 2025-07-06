package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.TicketsPorTecnicoDTO;
import DSWS2Grupo4.repository.AsignacionIncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticasService {

    @Autowired
    private AsignacionIncidenciaRepository asignacionRepo;

    public List<TicketsPorTecnicoDTO> obtenerTicketsPorTecnico() {;
        return asignacionRepo.contarTicketsAtendidosPorTecnico();
    }
}

