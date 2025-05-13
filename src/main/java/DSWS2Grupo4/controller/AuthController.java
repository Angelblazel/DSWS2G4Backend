package DSWS2Grupo4.controller;

import DSWS2Grupo4.DTO.AuthResponse;
import DSWS2Grupo4.DTO.LoginRequest;
import DSWS2Grupo4.DTO.RegisterRequest;
import DSWS2Grupo4.service.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:4200"})

public class AuthController {
    private final AuthService authService;
    
    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    
    @PostMapping(value="registro")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    
}
