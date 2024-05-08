package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Jugador")

public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlayer;
    @Column(name = "NombreJugador")
    private String name;
    @Column(name = "FechaRegistro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToMany(mappedBy = "jugadorEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GameEntity> listaPartidas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_User", referencedColumnName = "userID")
    private UserEntity userEntity;

    public void agregarPartida(GameEntity juego){
        listaPartidas.add(juego);
    }
    public float porcentaje(){
        float porcentajeExito = 0.0f;
        if(listaPartidas != null & !listaPartidas.isEmpty()){
            long totalPartidas = listaPartidas.size();
            long partidasGanadas = listaPartidas.stream().filter(GameEntity::partidaGanada).count();
            porcentajeExito = (float) partidasGanadas/totalPartidas * 100;
        }
        return porcentajeExito;
    }

}
