package DSWS2Grupo4.service;

import DSWS2Grupo4.dto.HistorialEquipoDTO;
import DSWS2Grupo4.model.HistorialEquipo;
import DSWS2Grupo4.repository.HistorialEquipoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialEquipoService {

    private final HistorialEquipoRepository historialEquipoRepository;

    public HistorialEquipoService(HistorialEquipoRepository historialEquipoRepository) {
        this.historialEquipoRepository = historialEquipoRepository;
    }

    public List<HistorialEquipoDTO> buscarPorPalabraClave(String palabraClave) {
        List<HistorialEquipo> resultados = historialEquipoRepository.buscarPorPalabraClave(palabraClave);
        return resultados.stream()
            .map(h -> HistorialEquipoDTO.builder()
                .id(h.getId())
                .codigoEquipo(h.getIncidencia().getUsuarioSolicitante().getEquipo().getCodigoEquipo())
                .descripcionProblema(h.getIncidencia().getProblemaSubcategoria().getDescripcionProblema())
                .nombreCategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getCategoria().getNombreCategoria())
                .nombreSubcategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getNombreSubcategoria())
                .solucionProblema(h.getSolucion().getSolucionProblema())
                .palabrasClave(h.getPalabrasClave())
                .fechaSolucion(h.getFechaSolucion())
                .build())
            .collect(Collectors.toList());
    }
    
    public List<HistorialEquipoDTO> obtenerTodoElHistorial() {
        List<HistorialEquipo> resultados = historialEquipoRepository.findAll();
        return resultados.stream()
                .map(h -> HistorialEquipoDTO.builder()
                    .id(h.getId())
                    .codigoEquipo(h.getIncidencia().getUsuarioSolicitante().getEquipo().getCodigoEquipo())
                    .descripcionProblema(h.getIncidencia().getProblemaSubcategoria().getDescripcionProblema())
                    .nombreCategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getCategoria().getNombreCategoria())
                    .nombreSubcategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getNombreSubcategoria())
                    .solucionProblema(h.getSolucion().getSolucionProblema())
                    .palabrasClave(h.getPalabrasClave())
                    .fechaSolucion(h.getFechaSolucion())
                    .build())
                .collect(Collectors.toList());
    }
    
    public List<HistorialEquipoDTO> buscarPorCodigoEquipo(String codigoEquipo) {
        List<HistorialEquipo> resultados = historialEquipoRepository.buscarPorCodigoEquipo(codigoEquipo);
        return resultados.stream()
            .map(h -> HistorialEquipoDTO.builder()
                .id(h.getId())
                .codigoEquipo(h.getIncidencia().getUsuarioSolicitante().getEquipo().getCodigoEquipo())
                .descripcionProblema(h.getIncidencia().getProblemaSubcategoria().getDescripcionProblema())
                .nombreCategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getCategoria().getNombreCategoria())
                .nombreSubcategoria(h.getIncidencia().getProblemaSubcategoria().getSubcategoria().getNombreSubcategoria())
                .solucionProblema(h.getSolucion().getSolucionProblema())
                .palabrasClave(h.getPalabrasClave())
                .fechaSolucion(h.getFechaSolucion())
                .build())
            .collect(Collectors.toList());
    }

}
