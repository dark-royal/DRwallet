package africa.semicolon.wallet.domain.service;

import africa.semicolon.wallet.application.port.input.TransactionUseCase.CreateTransactionUseCase;
import africa.semicolon.wallet.application.port.input.TransactionUseCase.GetAllTransactionByUserIdUseCase;
import africa.semicolon.wallet.application.port.output.TransactionOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.TransactionPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


public class TransactionService implements CreateTransactionUseCase, GetAllTransactionByUserIdUseCase {

    private final TransactionOutputPort transactionOutputPort;
    private final UserRepository userRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;


    public TransactionService(TransactionOutputPort transactionOutputPort, UserRepository userRepository, TransactionPersistenceMapper transactionPersistenceMapper) {
        this.transactionOutputPort = transactionOutputPort;

        this.userRepository = userRepository;
        this.transactionPersistenceMapper = transactionPersistenceMapper;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction = transactionOutputPort.saveTransaction(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactionByUserId(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        List<TransactionEntity> entities =  transactionOutputPort.getAllTransactionById(userId);
        return entities.stream().map(transactionPersistenceMapper::toTransaction).toList();

    }
}