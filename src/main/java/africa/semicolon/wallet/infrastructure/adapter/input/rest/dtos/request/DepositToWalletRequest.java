package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class DepositToWalletRequest {
    private BigDecimal amount;
    private String currency;
    private Long userId;
    private Wallet walletId;
}
