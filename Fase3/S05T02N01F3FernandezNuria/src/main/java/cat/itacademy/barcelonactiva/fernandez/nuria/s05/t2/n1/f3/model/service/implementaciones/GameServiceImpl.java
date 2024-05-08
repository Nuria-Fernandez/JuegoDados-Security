package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.implementaciones;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions.PlayerNotExists;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces.GameService;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.utils.ConversorGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerServiceImpl playerService;
    @Transactional
    @Override
    public GameDTO jugarJuego(int playerId, String userEmail) {
        int userId = playerService.findUserById(userEmail);
        PlayerEntity playerEntity = playerRepository.findByUserEntity_UserIDAndIdPlayer(userId, playerId);
        if(playerEntity == null){
            throw new PlayerNotExists("No se ha encontrado ningún jugador");
        }
        GameEntity gameEntity = new GameEntity();
        partida(gameEntity);
        gameEntity.setJugadorEntity(playerEntity);
        gameEntity.setFechaOcurrencia(LocalDate.now());
        playerEntity.agregarPartida(gameEntity);
        gameRepository.save(gameEntity);
        return ConversorGame.convertirDesdeGameEntity(gameEntity);
    }

    @Transactional
    @Override
    public String borrarJuegos(int playerId, String userEmail) {
        int userId = playerService.findUserById(userEmail);
        PlayerEntity playerEntity = playerRepository.findByUserEntity_UserIDAndIdPlayer(userId, playerId);
        if(playerEntity == null){
            throw new PlayerNotExists("No se encuentra el jugador");
        }
        List<GameEntity> listaPartidas = playerEntity.getListaPartidas();
        if(!listaPartidas.isEmpty()){
            listaPartidas.clear();
            gameRepository.deleteAll(listaPartidas);
            return "Se han eliminado las partidas";
        }else{
            throw new GameNotFound("No se han encontrado partidas");
        }
    }

    @Override
    public List<GameDTO> listaJugadasByJugador(int playerId) {
        PlayerEntity playerEntity = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotExists("No se ha encontrado el jugador"));
        List<GameEntity> listaPartidas = gameRepository.findByJugadorEntity_IdPlayer(playerId);
        if(!listaPartidas.isEmpty()){
            return listaPartidas.stream().map(ConversorGame::convertirDesdeGameEntity).collect(Collectors.toList());
        }else {
            throw new GameNotFound("No se han encontrado partifas");
        }
    }

    @Override
    public void partida(GameEntity juegoEntity) {
        Random random = new Random();
        int dado1 = random.nextInt(6)+1;
        int dado2 = random.nextInt(6)+1;
        juegoEntity.setDado1(dado1);
        juegoEntity.setDado2(dado2);
        juegoEntity.setGanada(juegoEntity.partidaGanada());


    }
}
