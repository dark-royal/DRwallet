package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.walletUseCases.CreateWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.DepositToWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.FindWalletByIdUsesCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.WithdrawUseCase;
import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.InitializePaymentResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.PaymentVerificationResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferRecipientResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransferResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static africa.semicolon.wallet.infrastructure.adapter.paystack.PayStackAdapter.createRecipient;

public class WalletService implements CreateWalletUseCase, FindWalletByIdUsesCase, DepositToWalletUseCase, WithdrawUseCase {

    private final WalletOutputPort walletOutputPort;

        private final PaystackPaymentOutputPort paystackPaymentOutputPort;
       private final WalletRepository walletRepository;
       private final UserWalletMediator userWalletMediator;
       private final PayStackAdapter payStackAdapter;


    public WalletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository, UserWalletMediator userWalletMediator, PayStackAdapter payStackAdapter) {
        this.walletOutputPort = walletOutputPort;
        this.paystackPaymentOutputPort = paystackPaymentOutputPort;
        this.walletRepository = walletRepository;
        this.userWalletMediator = userWalletMediator;
        this.payStackAdapter = payStackAdapter;
    }



    @Override
    public Wallet createWallet(Wallet wallet) throws WalletAlreadyExistAlreadyException {
        verifyWalletExistence(wallet.getId());
        wallet = walletOutputPort.saveWallet(wallet);
        return wallet;
    }

    private void verifyWalletExistence(Long id) throws WalletAlreadyExistAlreadyException {
        if(walletOutputPort.getWalletById(id).isPresent()){
            throw new WalletAlreadyExistAlreadyException("Wallet exists already");
        }
    }

    @Override
    public Wallet findWalletById(Long id) throws WalletNotFoundException {
       Optional<Wallet> wallet = walletOutputPort.getWalletById(id);
       if(wallet.isPresent()){
           return wallet.get();
       }
       else {
           throw new WalletNotFoundException("Wallet not found");
       }
    }

    @Override
    public void depositToWallet(WalletEntity wallet, BigDecimal amount, String email) throws WalletNotFoundException {
        wallet = walletRepository.findById(wallet.getUserId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        InitializePaymentDto initializePaymentDto = InitializePaymentDto.builder()
                .amount(amount)
                .email(email)
                .currency("USD")
                .build();

        InitializePaymentResponse response = paystackPaymentOutputPort.initializePayment(initializePaymentDto);
        response.setMessage("Deposit to wallet successful");

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }


    @Override
    public void withdrawFromWallet(WalletEntity wallet, BigDecimal amount, String accountNumber, String bankCode) throws Exception {
        wallet = walletRepository.findById(wallet.getUserId().getId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        TransferRecipientResponse recipientResponse = createRecipient(wallet.getUserId().getName(), accountNumber, bankCode);
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




