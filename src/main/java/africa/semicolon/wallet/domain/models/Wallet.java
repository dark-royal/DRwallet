package africa.semicolon.wallet.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Wallet {
    private Long id;
    private BigDecimal balance;
    private Long userId;
    private String currency;
    private LocalDateTime createdAt;
}
