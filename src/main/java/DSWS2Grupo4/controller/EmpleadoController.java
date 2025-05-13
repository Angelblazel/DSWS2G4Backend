package DSWS2Grupo4.controller;
import DSWS2Grupo4.DTO.EmpleadoDTO;
import DSWS2Grupo4.DTO.EmpleadoRequest;
import DSWS2Grupo4.DTO.EmpleadoResponse;
import DSWS2Grupo4.service.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:4200"})
public class EmpleadoController {
    private final EmpleadoService userService;
    
    @GetMapping(value = "{id}")
    public ResponseEntity<EmpleadoDTO> getUser(@PathVariable Integer id)
    {
        EmpleadoDTO userDTO = userService.getUser(id);
        if (userDTO==null)
        {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping()
    public ResponseEntity<EmpleadoResponse> updateUser(@RequestBody EmpleadoRequest userRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
