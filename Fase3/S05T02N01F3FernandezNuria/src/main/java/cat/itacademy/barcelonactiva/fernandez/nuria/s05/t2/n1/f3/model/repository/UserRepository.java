package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.repository;

import cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.model.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    boolean existsByNameIgnoreCase (String name);
}
