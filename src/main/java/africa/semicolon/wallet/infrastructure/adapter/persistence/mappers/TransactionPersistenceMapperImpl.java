package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.Transaction;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;

public class TransactionPersistenceMapperImpl implements TransactionPersistenceMapper {
    @Override

    public TransactionEntity toTransactionEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .type(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .userId(transaction.getUserId())
                .timestamp(transaction.getCreatedAt())
                .walletId(transaction.getWalletId())
                .build();
    }


    @Override
    public Transaction toTransaction(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .transactionType(transactionEntity.getType())
                .id(transactionEntity.getId())
                .createdAt(transactionEntity.getTimestamp())
                .description(transactionEntity.getReference())
                .walletId(transactionEntity.getWalletId())
                .status(transactionEntity.getStatus())
                .amount(transactionEntity.getAmount())
                .build();
    }
}
