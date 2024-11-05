package africa.semicolon.wallet.infrastructure.adapter.persistence;

import africa.semicolon.wallet.application.port.output.TransactionOutputPort;
import africa.semicolon.wallet.domain.exceptions.TransactionNotFoundException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.TransactionPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.TransactionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    public TransactionPersistenceAdapter(TransactionRepository transactionRepository, TransactionPersistenceMapper transactionPersistenceMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionPersistenceMapper = transactionPersistenceMapper;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = transactionPersistenceMapper.toTransactionEntity(transaction);
        transactionEntity = transactionRepository.save(transactionEntity);
        return transactionPersistenceMapper.toTransaction(transactionEntity);
    }

    @Override
    public TransactionEntity getTransactionById(Long transactionId) throws TransactionNotFoundException {
        Optional<TransactionEntity> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new TransactionNotFoundException("Transaction not found");
        }

    }

    @Override
    public List<TransactionEntity> getAllTransactionById(Long userId) {
        return transactionRepository.findByUserId(userId);
    }


}

