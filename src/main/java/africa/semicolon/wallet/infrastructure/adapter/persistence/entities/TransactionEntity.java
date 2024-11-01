package africa.semicolon.wallet.infrastructure.adapter.persistence.entities;

import africa.semicolon.wallet.domain.models.Status;
import africa.semicolon.wallet.domain.models.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long walletId ;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Status status;

    @Column
    private String reference;


}