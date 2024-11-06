package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.input.TransactionUseCase.CreateTransactionUseCase;
import africa.semicolon.wallet.application.port.input.TransactionUseCase.GetAllTransactionByUserIdUseCase;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.GetAllTransactionRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.GetAllTransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.TransactionRestMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController("/transaction")
public class TransactionRestAdapter {

    private final TransactionRestMapper transactionRestMapper;
    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetAllTransactionByUserIdUseCase getAllTransactionByUserIdUseCase;

    public TransactionRestAdapter(TransactionRestMapper transactionRestMapper, CreateTransactionUseCase createTransactionUseCase, GetAllTransactionByUserIdUseCase getAllTransactionByUserIdUseCase) {
        this.transactionRestMapper = transactionRestMapper;
        this.createTransactionUseCase = createTransactionUseCase;
        this.getAllTransactionByUserIdUseCase = getAllTransactionByUserIdUseCase;
    }


    @PostMapping(value = "/create")
    public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        Transaction transaction = transactionRestMapper.toTransaction(createTransactionRequest);
        transaction = createTransactionUseCase.createTransaction(transaction);
            return  new ResponseEntity<>(transactionRestMapper.toCreateTransactionResponse(transaction), HttpStatus.CREATED);
    }



    @GetMapping("/get-all-transaction")
    public ResponseEntity<?> getAllTransaction(@RequestBody GetAllTransactionRequest getAllTransactionRequest) throws UserNotFoundException {
        Long userId = transactionRestMapper.toUserId(getAllTransactionRequest);
        List<Transaction> transactions = getAllTransactionByUserIdUseCase.getAllTransactionByUserId(userId);
        List<GetAllTransactionResponse> response = transactionRestMapper.toGetAllTransactionResponse(transactions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
