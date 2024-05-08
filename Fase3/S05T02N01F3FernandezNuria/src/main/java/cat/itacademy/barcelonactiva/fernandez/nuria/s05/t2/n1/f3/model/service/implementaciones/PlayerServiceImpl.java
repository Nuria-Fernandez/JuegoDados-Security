package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.PlayerAlreadyExists;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.PlayerNotExists;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.PlayersNotFound;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces.PlayerService;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.utils.ConversorPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    UserRepository userRepository;

    public PlayerDTO agregarPlayer(PlayerDTO playerDTO, String userEmail) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(userEmail).orElseThrow(() -> new UserNotFound("El usuario con " + userEmail + " no se encuentra"));


        if (playerDTO.getName() == null || playerDTO.getName().isBlank() || playerDTO.getName().equalsIgnoreCase("ANONIMO")) {
            playerDTO.setName("Anonimo");
        } else if (playerRepository.existsByNameIgnoreCase(playerDTO.getName())) {
            throw new PlayerAlreadyExists("Ya existe un jugador con ese nombre");
        }
        return crearPlayer(playerDTO, userEntity);
    }

    public PlayerDTO crearPlayer(PlayerDTO playerDTO, UserEntity user) {
        playerDTO.setName(playerDTO.getName());
        playerDTO.setFechaRegistro(LocalDateTime.now());
        PlayerEntity playerEntity = ConversorPlayer.convertirParaJugadorEntity(playerDTO);
        playerEntity.setUserEntity(user);
        playerRepository.save(playerEntity);
        return ConversorPlayer.convertirDesdeJugadorEntity(playerEntity);
    }

    @Override
    public PlayerDTO actualizar(int playerId, PlayerDTO jugadorDTO, String userEmail) {
        int userId = findUserById(userEmail);
        PlayerEntity playerEntityActualizado = playerRepository.findByUserEntity_UserIDAndIdPlayer(userId, playerId);
        if (jugadorDTO == null) {
            throw new PlayerNotExists("No se ha encontrado el jugador con el id " + playerId);
        }

        if (jugadorDTO.getName() == null || jugadorDTO.getName().isBlank()) {
            jugadorDTO.setName("ANONIMO");
        } else if (playerRepository.existsByNameIgnoreCase(jugadorDTO.getName())) {
            throw new PlayerAlreadyExists("Ya existe un jugador con ese nombre");
        }
            playerEntityActualizado.setName(jugadorDTO.getName());
            playerRepository.save(playerEntityActualizado);
            return ConversorPlayer.convertirDesdeJugadorEntity(playerEntityActualizado);


    }


    @Override
    public String borrarPlayer(int playerid, String userEmail) {
       int userId = findUserById(userEmail);
       PlayerEntity playerEntityDelete = playerRepository.findByUserEntity_UserIDAndIdPlayer(userId, playerid);
       if(playerEntityDelete == null) {
           throw new PlayerNotExists("No existe ningún jugador con el id " + playerid);
       }
       playerRepository.deleteById(playerid);
       return "Se ha borrado el jugador existosamente";

    }
    public int findUserById(String userEmail){
        UserEntity userEntity = userRepository.findUserEntityByEmail(userEmail).orElseThrow(() -> new UserNotFound("User not found."));
        return userEntity.getUserID();
    }


    @Override
    public List<PlayerDTO> getAll(String userEmail) {
        int userId = findUserById(userEmail);
        List<PlayerEntity> jugadores = playerRepository.findByUserEntity_UserID(userId);
        if(jugadores.isEmpty()) {
            throw new PlayersNotFound("No se han econtrado los jugadores");
        }
        return jugadores.stream().map(ConversorPlayer::convertirDesdeJugadorEntity).collect(Collectors.toList());
    }

    @Override
    public List<PlayerDTO> getAllconPorcentajePorJugador(String userEmail) {
        int userId = findUserById(userEmail);
        List<PlayerEntity> jugadores = playerRepository.findByUserEntity_UserID(userId);
        if(jugadores.isEmpty()){
            throw new PlayersNotFound("No se han enocntrado los jugadores");
        }
        List<PlayerDTO> jugadoresDTO = new ArrayList<>();
        for(PlayerEntity playerEntity : jugadores){
            PlayerDTO playerDTO = ConversorPlayer.convertirDesdeJugadorEntity(playerEntity);
            playerDTO.setPorcentaje(playerEntity.porcentaje());
            jugadoresDTO.add(playerDTO);
        }
        return jugadoresDTO;
    }

    @Override
    public PlayerDTO findById(int id) {
        Optional<PlayerEntity> optionalJugadorEntity = playerRepository.findById(id);
        if(optionalJugadorEntity.isPresent()){
            return ConversorPlayer.convertirDesdeJugadorEntity(optionalJugadorEntity.get());
        }else{
            throw new PlayerNotExists("No se ha encontrado el jugador con ID " + id);
        }
    }

    @Override
    public double porcentajeMedioExito() {
        List<PlayerEntity>jugadores = playerRepository.findAll();
        if(!jugadores.isEmpty()){
            double tasaExito = jugadores.stream().mapToDouble(PlayerEntity::porcentaje).sum();
            return Math.round(tasaExito/ jugadores.size());
        }else{
            throw new PlayersNotFound("No hay jugadores");
        }
    }

    @Override
    public PlayerDTO mejorPorcentajeExito() {
        List<PlayerEntity> listaJugadorEntity = playerRepository.findAll();
        if(!listaJugadorEntity.isEmpty()) {
            PlayerEntity jugadorGanador = listaJugadorEntity.stream().max(Comparator.comparingDouble(PlayerEntity::porcentaje))
                    .orElseThrow(() -> new PlayersNotFound("No hay jugadores en la lista"));

            return ConversorPlayer.convertirDesdeJugadorEntity(jugadorGanador);
        }else {
            throw new PlayersNotFound("No se han encontrado jugadores para hacer el cálculo");
        }
    }

    @Override
    public PlayerDTO peorPorcentajeExito() {
        List<PlayerEntity> listaJugadorEntity = playerRepository.findAll();
        if(!listaJugadorEntity.isEmpty()) {
            PlayerEntity jugadorGanador = listaJugadorEntity.stream().min(Comparator.comparingDouble(PlayerEntity::porcentaje))
                    .orElseThrow(() -> new PlayersNotFound("No hay jugadores en la lista"));

            return ConversorPlayer.convertirDesdeJugadorEntity(jugadorGanador);
        } else{
            throw new PlayersNotFound("No se han encontrado jugadores para hacer el cálculo");
        }
    }
}




