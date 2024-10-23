package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.walletUseCases.CreateWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.DepositToWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.FindWalletByIdUsesCase;
import africa.semicolon.wallet.application.port.output.PaystackPaymentOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.InitializePaymentDto;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.InitializePaymentResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.PaymentVerificationResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletService implements CreateWalletUseCase, FindWalletByIdUsesCase, DepositToWalletUseCase {

    private final WalletOutputPort walletOutputPort;

        private final PaystackPaymentOutputPort paystackPaymentOutputPort;
       private final WalletRepository walletRepository;


    public WalletService(WalletOutputPort walletOutputPort, PaystackPaymentOutputPort paystackPaymentOutputPort, WalletRepository walletRepository) {
        this.walletOutputPort = walletOutputPort;
        this.paystackPaymentOutputPort = paystackPaymentOutputPort;
        this.walletRepository = walletRepository;
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
    public void depositToWallet(WalletEntity wallet, BigDecimal amount) throws WalletNotFoundException {
        wallet = walletRepository.findById(wallet.getUserId().getId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
                InitializePaymentDto initializePaymentDto = InitializePaymentDto.builder()
                        .amount(amount)
                        .email(wallet.getUserId().getEmail())
                        .currency("USD")
                        .build();
               InitializePaymentResponse response = paystackPaymentOutputPort.initializePayment(initializePaymentDto);
               response.setMessage("Deposit to wallet successful");
               wallet.setBalance(wallet.getBalance().add(amount));
               walletRepository.save(wallet);
            }




    }




