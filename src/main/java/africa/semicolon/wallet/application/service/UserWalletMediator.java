package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;

public class UserWalletMediator {

    private  UserService userService;
    private  WalletService walletService;
    private UserRepository userRepository;

    public User createUserWithWallet(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        Wallet wallet = walletService.createWallet(user.getWallet());
        user.setWallet(wallet);
        return userService.createUser(user);
    }

    public String getUserEmail(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getEmail();
    }
}


