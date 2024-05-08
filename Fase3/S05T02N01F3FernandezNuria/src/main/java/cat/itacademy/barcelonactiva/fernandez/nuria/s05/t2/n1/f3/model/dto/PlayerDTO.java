package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private int idPlayer;
    private String name;
    private LocalDateTime fechaRegistro;
    private float porcentaje;

}
