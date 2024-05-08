package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    boolean existsByNameIgnoreCase(String name);
    PlayerEntity findByUserEntity_UserIDAndIdPlayer(int userId, int playerId);
    List<PlayerEntity> findByUserEntity_UserID(int userId);
}
