package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.controllers;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthRequest;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthResponse;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.UserDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authService.register(userDTO));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate (@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
