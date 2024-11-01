package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class CreateWalletResponse {
    private LocalDateTime createdAt;
    private Long WalletId;
    private Long userId;
    private BigDecimal balance;
}
