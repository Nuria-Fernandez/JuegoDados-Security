package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.UserAlreadyExists;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.Role;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthRequest;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.AuthResponse;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.UserDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse register(UserDTO userDTO) {
        if(userAlreadyExists(userDTO.getEmail())){
            throw new UserAlreadyExists("User already exists.");
        }
        var user = UserEntity.builder()
                .name(userDTO.getName())
                .secondName(userDTO.getSecondName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword()));
        var user = userRepository.findUserEntityByEmail(authRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
    private boolean userAlreadyExists(String username) {
        return userRepository.existsByNameIgnoreCase(username);
    }
}
