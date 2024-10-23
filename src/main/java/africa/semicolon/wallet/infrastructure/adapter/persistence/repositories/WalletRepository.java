package africa.semicolon.wallet.infrastructure.adapter.persistence.repositories;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
