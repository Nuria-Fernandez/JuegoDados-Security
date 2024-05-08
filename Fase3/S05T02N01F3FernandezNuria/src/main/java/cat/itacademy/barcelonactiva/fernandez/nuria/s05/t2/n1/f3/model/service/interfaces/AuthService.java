package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces;


import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthRequest;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthResponse;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.UserDTO;

public interface AuthService {
    AuthResponse register(UserDTO userDTO);
    AuthResponse authenticate(AuthRequest authRequest);
}
