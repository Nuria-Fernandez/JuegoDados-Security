package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.utils;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto.PlayerDTO;
import org.springframework.stereotype.Component;

@Component
public class ConversorPlayer {
    public static PlayerDTO convertirDesdeJugadorEntity(PlayerEntity jugadorEntity){
        PlayerDTO jugadorDTO = new PlayerDTO();

        jugadorDTO.setIdPlayer(jugadorEntity.getIdPlayer());
        jugadorDTO.setName(jugadorEntity.getName());
        jugadorDTO.setPorcentaje(jugadorEntity.porcentaje());

        return jugadorDTO;
    }
    public static PlayerEntity convertirParaJugadorEntity(PlayerDTO jugadorDTO){
        PlayerEntity jugadorEntity = new PlayerEntity();

        jugadorEntity.setIdPlayer(jugadorDTO.getIdPlayer());
        jugadorEntity.setName(jugadorDTO.getName());

        return jugadorEntity;
    }
}
