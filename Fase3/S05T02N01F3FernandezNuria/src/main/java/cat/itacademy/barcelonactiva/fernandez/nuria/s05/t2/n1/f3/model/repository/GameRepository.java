package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    List<GameEntity> findByJugadorEntity_IdPlayer(int playerId);
}
