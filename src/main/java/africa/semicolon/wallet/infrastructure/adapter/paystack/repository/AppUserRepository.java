package africa.semicolon.wallet.infrastructure.adapter.paystack.repository;

import africa.semicolon.wallet.infrastructure.adapter.paystack.models.Paystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<Paystack, Long> {

}
