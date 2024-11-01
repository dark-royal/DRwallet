package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import africa.semicolon.wallet.domain.models.Status;
import africa.semicolon.wallet.domain.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class CreateTransactionResponse {
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private TransactionType transactionType;
    private Status status;
    private Long walletId;
}
