package DSWS2Grupo4.service;

import DSWS2Grupo4.model.Repuesto;
import DSWS2Grupo4.repository.RepuestoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepuestoService {
    @Autowired
    private RepuestoRepository repuestoRepo;
    public List<Repuesto> listaRepuestos() {
        return repuestoRepo.findAll();
    }

    public Repuesto obtenerRepuestoPorId(Long id) {
        return repuestoRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Repuesto no encontrado con id: " + id));
    }

    public Repuesto obtenerRepuestoPorCodigo(String codigo) {
        return repuestoRepo.findByCodigoRepuesto(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Repuesto no encontrado con código: " + codigo));
    }

    public Repuesto registrarRepuesto(Repuesto repuesto) {
        return repuestoRepo.save(repuesto);
    }

    public Repuesto actualizarRepuesto(Long id, Repuesto repuestoActualizado) {
        Repuesto repuesto = obtenerRepuestoPorId(id);
        repuesto.setCantidad(repuestoActualizado.getCantidad());
        return repuestoRepo.save(repuesto);
    }

    public boolean eliminarRepuesto(Long id) {
        if (repuestoRepo.existsById(id)) {
            repuestoRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
