package africa.semicolon.wallet.infrastructure.adapter.persistence.repositories;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
