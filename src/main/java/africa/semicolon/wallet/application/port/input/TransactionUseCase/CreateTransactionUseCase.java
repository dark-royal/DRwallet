package africa.semicolon.wallet.application.port.input.TransactionUseCase;

import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.TransactionDto;

public interface CreateTransactionUseCase {
    Transaction createTransaction(Transaction transaction);

}
