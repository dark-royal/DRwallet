package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.domain.exceptions.TransactionNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;

import java.util.List;

public interface TransactionOutputPort {
    Transaction saveTransaction(Transaction transaction);
   TransactionEntity getTransactionById(Long transactionId) throws TransactionNotFoundException;

    List<TransactionEntity> getAllTransactionById(Long userId);

}
