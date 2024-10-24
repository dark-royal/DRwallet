package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;

public class UserWalletMediator {

    private  UserService userService;
    private  WalletService walletService;

//    public UserWalletMediator(UserService userService, WalletService walletService) {
//        this.userService = userService;
//        this.walletService = walletService;
//    }

    public User createUserWithWallet(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        Wallet wallet = walletService.createWallet(user.getWallet());
        user.setWallet(wallet);
        return userService.createUser(user);
    }
}