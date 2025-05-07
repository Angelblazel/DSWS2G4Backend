package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.LoginRequest;
import DSWS2Grupo4.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return authService.login(req.getUsername(), req.getPassword())
            .<ResponseEntity<?>>map(emp -> ResponseEntity.ok(emp))
            .orElseGet(() ->
                ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas o rol no autorizado")
            );
    }
}
