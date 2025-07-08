package DSWS2Grupo4.controller;
import DSWS2Grupo4.DTO.UsuarioDTO;
import DSWS2Grupo4.DTO.UsuarioRequest;
import DSWS2Grupo4.DTO.UsuarioResponse;
import DSWS2Grupo4.service.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UsuarioController {
    private final EmpleadoService userService;
    
    @GetMapping(value = "{id}")
    public ResponseEntity<UsuarioDTO> getUser(@PathVariable Integer id)
    {
        UsuarioDTO userDTO = userService.getUser(id);
        if (userDTO==null)
        {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping()
    public ResponseEntity<UsuarioResponse> updateUser(@RequestBody UsuarioRequest userRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
