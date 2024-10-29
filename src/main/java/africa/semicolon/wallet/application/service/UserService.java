package africa.semicolon.wallet.application.service;


import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.IncorrectPaaswordException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserService implements RegisterUserUseCase, EditProfileByNameUseCase, EditProfileByEmailUseCase, EditProfileByPassword, EditProfileByPhoneNumber,FindUserByEmailUsesCase,GetUserUseCase {

    private final UserOutputPort userOutputPort;
    private final WalletService walletService;
    private final WalletOutputPort walletOutputPort;


    public UserService(UserOutputPort userOutputPort, WalletService walletService, WalletOutputPort walletOutputPort) {
        this.userOutputPort = userOutputPort;
        this.walletService = walletService;
        this.walletOutputPort = walletOutputPort;
    }


    private void verifyUserExistence(String email) throws WalletAlreadyExistAlreadyException {
        if (userOutputPort.getUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("user exists already");
        }
    }


    @Override
    public User editProfileByName(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            foundUser.get().setName(user.getName());
            userOutputPort.saveUser(foundUser.get());
            return foundUser.get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByEmail(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            foundUser.get().setEmail(user.getEmail());
            userOutputPort.saveUser(foundUser.get());
            return foundUser.get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByPassword(User user) throws IncorrectPaaswordException, UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            if (user.getPassword().equals(foundUser.get().getPassword())) {
                foundUser.get().setPassword(user.getPassword());
                userOutputPort.saveUser(foundUser.get());
                return foundUser.get();
            } else {
                throw new IncorrectPaaswordException("Incorrect Username or password");
            }


        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByPhoneNumber(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            foundUser.get().setPhoneNumber(user.getPhoneNumber());
            userOutputPort.saveUser(foundUser.get());
            return foundUser.get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        verifyUserExistence(user.getEmail());

        Wallet newWallet = new Wallet();
        newWallet.setId(user.getId());
        newWallet.setBalance(BigDecimal.valueOf(0.0));

        log.info("Creating new wallet: {}", newWallet);

        Wallet wallet = walletOutputPort.saveWallet(newWallet);
        log.info("Wallet created: {}", wallet);

        user.setWallet(wallet);
        user.setCreatedOn(LocalDateTime.now());

        return userOutputPort.saveUser(user);
    }



    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
       Optional<User> user = userOutputPort.getUserByEmail(email);
       if(user.isPresent()){
           return user.get();
       }
       else{
           throw new UserNotFoundException("user not found");
       }
    }


    @Override
    public UserEntity getUserById(Long id) throws UserNotFoundException {
        UserEntity user = userOutputPort.getUserById(id);
        if(user != null){
           return user;
        }
        else {
            throw new UserNotFoundException("user not found");
        }

    }
}
