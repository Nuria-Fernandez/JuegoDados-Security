package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.controllers;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones.GameServiceImpl;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones.JwtService;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones.PlayerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {
    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private GameServiceImpl gameService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/crear")
    ResponseEntity<PlayerDTO> agregarJugador(@RequestBody PlayerDTO jugadorDTO, HttpServletRequest httpServletRequest){//Le pongo <?> porque devuelve dos ResponseEntity diferentes
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        PlayerDTO jugadorDTO1 = playerService.agregarPlayer(jugadorDTO, userEmail);
        return new ResponseEntity<>(jugadorDTO1, HttpStatus.CREATED);
    }
    @PutMapping("/actualizar/{id}")
    ResponseEntity<PlayerDTO> actualzarJugador(@PathVariable int id, @RequestBody PlayerDTO jugadorDTO, HttpServletRequest httpServletRequest){
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        PlayerDTO jugadorDTO1 = playerService.actualizar(id, jugadorDTO, userEmail);
        return ResponseEntity.ok(jugadorDTO1);
    }
    @PostMapping("/{id}/partidas")
    public ResponseEntity<GameDTO> jugarPartida(@PathVariable int id, HttpServletRequest httpServletRequest){
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        GameDTO juegoDTO = gameService.jugarJuego(id, userEmail);
        return new ResponseEntity<>(juegoDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}/eliminarPartidas")
    public ResponseEntity<String> eliminarPartidas(@PathVariable int id, HttpServletRequest httpServletRequest){
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        String mensaje = gameService.borrarJuegos(id, userEmail);
        return ResponseEntity.ok(mensaje);
    }
    @DeleteMapping("/{id}/borrarJugador")
    public ResponseEntity<String> eliminarJugador(@PathVariable int id, HttpServletRequest httpServletRequest){
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        String mensaje = playerService.borrarPlayer(id, userEmail);
        return ResponseEntity.ok(mensaje);
    }
    @GetMapping("/mostrarListadoPorcentajeJugador")
    public ResponseEntity<List<PlayerDTO>> mostrarListadoPorcentajeMedio(HttpServletRequest httpServletRequest){
        String token = jwtService.extraerToken(httpServletRequest);
        String userEmail = jwtService.getUserName(token);
        List<PlayerDTO>listaJugadores = playerService.getAllconPorcentajePorJugador(userEmail);
        return ResponseEntity.ok(listaJugadores);
    }
    @GetMapping("/{id}/mostrarJugadasPorJugador")
    public ResponseEntity<List<GameDTO>>partidasPorJugador(@PathVariable int id){
        List<GameDTO>listaJuegos = gameService.listaJugadasByJugador(id);
        return ResponseEntity.ok(listaJuegos);
    }
    @GetMapping("/ranking")
    public ResponseEntity<Double> porcentajeMedioTodosJugadores(){
        double porcentajeMedioExito = playerService.porcentajeMedioExito();
        return ResponseEntity.ok(porcentajeMedioExito);
    }
    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> peorPorcentaje(){
        PlayerDTO jugadorDTO = playerService.peorPorcentajeExito();
        return ResponseEntity.ok(jugadorDTO);
    }
    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> mejorPorcentaje(){
        PlayerDTO jugadorDTO = playerService.mejorPorcentajeExito();
        return ResponseEntity.ok(jugadorDTO);
    }



}
