package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.TransactionUseCase.CreateTransactionUseCase;
import africa.semicolon.wallet.application.port.input.TransactionUseCase.GetAllTransactionByUserIdUseCase;
import africa.semicolon.wallet.application.port.output.TransactionOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


public class TransactionService implements CreateTransactionUseCase, GetAllTransactionByUserIdUseCase {

    private final TransactionOutputPort transactionOutputPort;
    private final UserRepository userRepository;


    public TransactionService(TransactionOutputPort transactionOutputPort, UserRepository userRepository) {
        this.transactionOutputPort = transactionOutputPort;

        this.userRepository = userRepository;
    }

//    public Transaction createTransaction(TransactionDto transactionDto) {
//
//        TransactionEntity transaction = new TransactionEntity();
//        transaction.setUser(transactionDto.getUserId());
//        transaction.setAmount(transactionDto.getAmount());
//        transaction.setType(transactionDto.getType());
//        transaction.setTimestamp(LocalDateTime.now());
//        transaction.setStatus(PENDING);
//        transactionRepository.save(transaction);
//
//        if (transaction.getType() == TransactionType.DEPOSIT) {
//            WalletEntity wallet = walletRepository.findById(transactionDto.getUserId()).orElse(null);
//            if (wallet != null) {
//                wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
//                walletRepository.save(wallet);
//            }
//        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
//            Wallet wallet = walletRepository.findById(transactionDto.getUserId()).orElse(null);
//            if (wallet != null) {
//                wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
//                walletRepository.save(wallet);
//            }
//
//        }
//        return transaction;
//    }


    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction = transactionOutputPort.saveTransaction(transaction);
        return transaction;
    }


    @Override
    public List<TransactionEntity> getAllTransactionByUserId(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return transactionOutputPort.getAllTransactionById(userId);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}