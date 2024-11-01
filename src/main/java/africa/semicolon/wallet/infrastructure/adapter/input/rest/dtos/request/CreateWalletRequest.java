package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class CreateWalletRequest {
    private LocalDateTime createdAt;
    private Long WalletId;
    private Long userId;
    private BigDecimal balance;
}
