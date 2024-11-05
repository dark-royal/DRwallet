package africa.semicolon.wallet.domain.service;

import africa.semicolon.wallet.application.port.input.walletUseCases.*;
import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.InitializePaymentResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferRecipientResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;



@Slf4j
public class WalletService implements CreateWalletUseCase, FindWalletByIdUsesCase, DepositToWalletUseCase,WithdrawUseCase {


    private final WalletOutputPort walletOutputPort;

    private final PaystackPaymentOutputPort paystackPaymentOutputPort;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final UserOutputPort userOutputPort;
    private final PayStackAdapter payStackAdapter;
    private final UserPersistenceAdapter userPersistenceAdapter;



    public WalletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository, UserRepository userRepository, UserOutputPort userOutputPort, PayStackAdapter payStackAdapter, UserPersistenceAdapter userPersistenceAdapter) {
        this.walletOutputPort = walletOutputPort;
        this.paystackPaymentOutputPort = paystackPaymentOutputPort;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.userOutputPort = userOutputPort;
        this.payStackAdapter = payStackAdapter;
        this.userPersistenceAdapter = userPersistenceAdapter;
    }
    @Override
    public Wallet createWallet(Wallet wallet) throws WalletAlreadyExistAlreadyException {
        verifyWalletExistence(wallet.getId());
        wallet = walletOutputPort.saveWallet(wallet);

        return wallet;
    }

    @Override
    public Wallet findWalletById(Long id) throws WalletNotFoundException {
        Optional<Wallet> wallet = walletOutputPort.getWalletById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new WalletNotFoundException("Wallet not found");
        }
    }

    private void verifyWalletExistence(Long id) throws WalletAlreadyExistAlreadyException {
        if (walletOutputPort.getWalletById(id).isPresent()) {
            throw new WalletAlreadyExistAlreadyException("Wallet exists already");
        }
    }
    @Override
    public void depositToWallet(Wallet wallet, BigDecimal amount, Long userId) throws WalletNotFoundException, UserNotFoundException {
        wallet = walletOutputPort.getWalletById(wallet.getId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        InitializePaymentDto initializePaymentDto = InitializePaymentDto.builder()
                .amount(amount)
                .email(user.getEmail())
                .currency("NGN")
                .build();

        InitializePaymentResponse response = payStackAdapter.initializePayment(initializePaymentDto);
        log.info("This is the message{}", response);
        response.setMessage("Deposit to wallet successful");

        wallet.setBalance(wallet.getBalance().add(amount));
        walletOutputPort.saveWallet(wallet);
    }

    @Override
    public void withdrawFromWallet(WalletEntity wallet, BigDecimal amount, String accountNumber, String bankCode, Long userId) throws Exception {
        wallet = walletRepository.findById(wallet.getId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        UserEntity user = userPersistenceAdapter.getUserEntityById(userId);
        String userName = user.getFirstName();

        TransferRecipientResponse recipientResponse = payStackAdapter.createRecipient(userName, accountNumber, bankCode);
        String recipientCode = recipientResponse.data.getRecipientCode();

        TransferResponse transferResponse = payStackAdapter.initiateWithdrawal(amount, recipientCode, "Withdrawal from wallet");
        if (transferResponse.getStatus().equals("success")) {
            wallet.setBalance(wallet.getBalance().subtract(amount));
            walletRepository.save(wallet);
            System.out.println("Withdrawal successful: " + transferResponse.getData());
        } else {
            throw new Exception("Withdrawal failed: " + transferResponse.getMessage());
        }
    }
    }






