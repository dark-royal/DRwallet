package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response;

import africa.semicolon.wallet.domain.models.TransactionDetails;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter

public class TransactionResponse {
    private Long userId;

    public TransactionResponse(Long userId, List<TransactionDetails> transactions) {
        this.userId = userId;
        this.transactions = transactions;
    }

    private String userName;
    private List<TransactionDetails> transactions;
    private int transactionCount;
    private BigDecimal totalAmount;
}
