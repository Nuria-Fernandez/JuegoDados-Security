package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.service.interfaces;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO jugarJuego(int id, String userEmail);
    String borrarJuegos(int id, String userEmail);
    List<GameDTO> listaJugadasByJugador(int id);
    void partida(GameEntity juegoEntity);
}
