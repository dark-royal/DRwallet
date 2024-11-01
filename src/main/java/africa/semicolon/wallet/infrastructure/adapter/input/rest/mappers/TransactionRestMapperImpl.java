package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.GetAllTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.GetAllTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;

import java.util.List;

public class TransactionRestMapperImpl implements TransactionRestMapper{
    @Override
    public Transaction toTransaction(CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(createTransactionRequest.getTransactionType());
        transaction.setAmount(createTransactionRequest.getAmount());
        transaction.setStatus(createTransactionRequest.getStatus());
        transaction.setUserId(createTransactionRequest.getUserId());
        transaction.setWalletId(createTransactionRequest.getWalletId());
        return transaction;
    }

    @Override
    public CreateTransactionResponse toCreateTransactionResponse(Transaction transaction) {
        CreateTransactionResponse response = new CreateTransactionResponse();
        response.setAmount(transaction.getAmount());
        response.setStatus(transaction.getStatus());
        response.setUserId(transaction.getUserId());
        response.setWalletId(transaction.getWalletId());
        response.setTransactionType(transaction.getTransactionType());
        return response;
    }

    @Override
    public List<TransactionEntity> toGetTransaction(GetAllTransactionRequest getAllTransactionRequest) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(getAllTransactionRequest.getUserId());
        return List.of(transactionEntity);
    }

    @Override
    public GetAllTransactionResponse toGetAllTransactionResponse(List<TransactionEntity> transaction) {
        GetAllTransactionResponse response = new GetAllTransactionResponse();
        response.setStatus(transaction.getFirst().getStatus());
        response.setAmount(transaction.getFirst().getAmount());
        response.setUserId(transaction.getFirst().getUserId());
        response.setUserId(transaction.getFirst().getUserId());
        return response;
    }
}
