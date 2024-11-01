package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class DepositToWalletResponse {
    private String description;
    private BigDecimal amount;
    private Long userId;
    private Long walletId;
}
