package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.Transaction;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionPersistenceMapper {
    TransactionEntity toTransactionEntity(Transaction transaction);
    Transaction toTransaction(TransactionEntity transactionEntity);
}
