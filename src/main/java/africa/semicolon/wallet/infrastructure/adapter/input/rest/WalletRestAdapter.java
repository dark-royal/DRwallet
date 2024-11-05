package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.input.walletUseCases.CreateWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.DepositToWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.FindWalletByIdUsesCase;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.DepositToWalletRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindWalletRequest;

import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.DepositToWalletResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindWalletResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.WalletRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletRestAdapter {

    private final WalletRestMapper walletRestMapper;
    private final CreateWalletUseCase createWalletUseCase;
    private final FindWalletByIdUsesCase findWalletByIdUsesCase;
    private final DepositToWalletUseCase depositToWalletUseCase;

    @PostMapping("/createWallet")
    public ResponseEntity<CreateWalletResponse> createWallet(@RequestBody @Validated final CreateWalletRequest createWalletRequest) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        Wallet wallet = walletRestMapper.toWallet(createWalletRequest);
        wallet = createWalletUseCase.createWallet(wallet);
        return new ResponseEntity<>(walletRestMapper.tocreateWalletResponse(wallet), HttpStatus.CREATED);
    }

    public WalletRestAdapter(WalletRestMapper walletRestMapper, CreateWalletUseCase createWalletUseCase, FindWalletByIdUsesCase findWalletByIdUsesCase, DepositToWalletUseCase depositToWalletUseCase) {
        this.walletRestMapper = walletRestMapper;
        this.createWalletUseCase = createWalletUseCase;
        this.findWalletByIdUsesCase = findWalletByIdUsesCase;
        this.depositToWalletUseCase = depositToWalletUseCase;
    }


    @GetMapping("/find")
    public ResponseEntity<FindWalletResponse> findWalletById(@RequestBody FindWalletRequest findWalletRequest) throws WalletNotFoundException {
        Wallet wallet = walletRestMapper.toFindWallet(findWalletRequest);
        wallet = findWalletByIdUsesCase.findWalletById(wallet.getId());
        return new ResponseEntity<>(walletRestMapper.tofindWalletResponse(wallet), HttpStatus.OK);

    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositToWalletResponse> deposit(@RequestBody DepositToWalletRequest depositToWalletRequest) throws UserNotFoundException, WalletNotFoundException {
        Wallet wallet = walletRestMapper.toDepositToWallet(depositToWalletRequest);
        wallet.setBalance(depositToWalletRequest.getAmount());
        wallet.setId(depositToWalletRequest.getWalletId().getId());
        BigDecimal amount = depositToWalletRequest.getAmount();
        Long userId = depositToWalletRequest.getUserId();
        depositToWalletUseCase.depositToWallet(wallet,amount,userId);
        return new ResponseEntity<>(walletRestMapper.toDepositToWalletResponse(wallet),HttpStatus.OK);

    }
}



