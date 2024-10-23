package africa.semicolon.wallet.infrastructure.adapter.persistence.repositories;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
