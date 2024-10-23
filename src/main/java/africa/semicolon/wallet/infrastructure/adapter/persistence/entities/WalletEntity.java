package africa.semicolon.wallet.infrastructure.adapter.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity userId;
    private BigDecimal balance;
}
