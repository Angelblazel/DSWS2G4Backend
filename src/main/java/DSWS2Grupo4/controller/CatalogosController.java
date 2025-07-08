package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.CategoriaDTO;
import DSWS2Grupo4.DTO.ProblemaDTO;
import DSWS2Grupo4.DTO.SubcategoriaDTO;
import DSWS2Grupo4.repository.CategoriaRepository;
import DSWS2Grupo4.repository.ProblemaSubcategoriaRepository;
import DSWS2Grupo4.repository.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
@CrossOrigin(origins = "*")
public class CatalogosController {

    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private SubcategoriaRepository subcategoriaRepo;
    @Autowired
    private ProblemaSubcategoriaRepository problemaRepo;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaRepo.findAllCategorias());
    }

    @GetMapping("/subcategorias/{categoriaId}")
    public ResponseEntity<List<SubcategoriaDTO>> listarSubcategorias(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(subcategoriaRepo.findByCategoriaId(categoriaId));
    }

    @GetMapping("/problemas/{subcategoriaId}")
    public ResponseEntity<List<ProblemaDTO>> listarProblemas(@PathVariable Long subcategoriaId) {
        return ResponseEntity.ok(problemaRepo.findBySubcategoriaId(subcategoriaId));
    }
}
