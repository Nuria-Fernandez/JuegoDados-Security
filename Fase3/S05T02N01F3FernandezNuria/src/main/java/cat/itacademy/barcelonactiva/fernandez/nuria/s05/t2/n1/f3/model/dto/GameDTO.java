package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private int gameId;
    private int dado1;
    private int dado2;
    private boolean ganada;
}
