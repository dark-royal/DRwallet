package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.GetAllTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.GetAllTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;



public interface TransactionRestMapper {

        Transaction toTransaction(CreateTransactionRequest createTransactionRequest);

        CreateTransactionResponse toCreateTransactionResponse(Transaction transaction);

        Long toUserId(GetAllTransactionRequest getAllTransactionRequest);

        List<GetAllTransactionResponse> toGetAllTransactionResponse(List<Transaction> transactions);
    }




