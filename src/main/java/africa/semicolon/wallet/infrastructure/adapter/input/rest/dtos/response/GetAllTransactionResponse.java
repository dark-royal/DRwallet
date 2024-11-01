package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import africa.semicolon.wallet.domain.models.Status;
import africa.semicolon.wallet.domain.models.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter

public class GetAllTransactionResponse {
    private Long userId;
    private TransactionType transactionType;
    private Status status;
    private BigDecimal amount;
}
