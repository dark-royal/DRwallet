package africa.semicolon.wallet.application.port.input.TransactionUseCase;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;

import java.util.List;

public interface GetAllTransactionByUserIdUseCase {

    List<Transaction> getAllTransactionByUserId(Long userId) throws UserNotFoundException;

}
