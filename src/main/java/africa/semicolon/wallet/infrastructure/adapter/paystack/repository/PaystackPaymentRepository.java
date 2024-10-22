package africa.semicolon.wallet.infrastructure.adapter.paystack.repository;

import africa.semicolon.wallet.infrastructure.adapter.paystack.models.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaystackPaymentRepository  extends JpaRepository<PaymentPaystack, Long> {
}
