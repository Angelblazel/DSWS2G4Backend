package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.ProblemasFrecuentesDTO;
import DSWS2Grupo4.DTO.TicketsPorTecnicoDTO;
import DSWS2Grupo4.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estadisticas")
// @CrossOrigin(origins = "*")
public class EstadisticaController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/tickets-tecnico")
    public ResponseEntity<List<TicketsPorTecnicoDTO>> ticketsPorTecnico() {
        List<TicketsPorTecnicoDTO> datos = estadisticasService.obtenerTicketsPorTecnico();
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/problemas-frecuentes")
    public ResponseEntity<List<ProblemasFrecuentesDTO>> getProblemasFrecuentes() {
        List<ProblemasFrecuentesDTO> lista = estadisticasService.obtenerProblemasMasFrecuentes();
        return ResponseEntity.ok(lista);
    }

}

