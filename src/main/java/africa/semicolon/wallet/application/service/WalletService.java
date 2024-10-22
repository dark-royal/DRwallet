package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.FindUserByEmailUsesCase;
import africa.semicolon.wallet.application.port.input.userUseCases.GetUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.RegisterUserUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.CreateWalletUseCase;
import africa.semicolon.wallet.application.port.input.walletUseCases.FindWalletByIdUsesCase;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;

import java.util.Optional;

public class WalletService implements CreateWalletUseCase, FindWalletByIdUsesCase {

    private final WalletOutputPort walletOutputPort;
    private final FindWalletByIdUsesCase findWalletByIdUseCase;


    public WalletService(WalletOutputPort walletOutputPort, FindWalletByIdUsesCase findWalletByIdUseCase) {
        this.walletOutputPort = walletOutputPort;
        this.findWalletByIdUseCase = findWalletByIdUseCase;
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
}
