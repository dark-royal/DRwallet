package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransactionResponse;

import java.util.List;

public interface ViewAllTransactionUseCase {
    TransactionResponse viewAllTransactions(Long userId) throws UserNotFoundException;


}
