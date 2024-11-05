package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;

import java.util.List;

public interface ViewAllTransactionUseCase {
    List<Transaction> viewAllTransactions(Long userId) throws UserNotFoundException;

}
