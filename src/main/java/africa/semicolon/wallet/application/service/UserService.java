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
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserService implements RegisterUserUseCase, EditProfileByNameUseCase, EditProfileByEmailUseCase, EditProfileByPassword, EditProfileByPhoneNumber,FindUserByEmailUsesCase,GetUserUseCase {

    private final UserOutputPort userOutputPort;
    private final WalletService walletService;
    private final WalletOutputPort walletOutputPort;
    private final TransactionService transactionService;
    private final KeycloakUserService keycloakUserService;
    private final UserEntity userEntity;
    private final UserPersistenceMapper userPersistenceMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserOutputPort userOutputPort, WalletService walletService, WalletOutputPort walletOutputPort, TransactionService transactionService, KeycloakUserService keycloakUserService, UserEntity userEntity, UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder) {
        this.userOutputPort = userOutputPort;
        this.walletService = walletService;
        this.walletOutputPort = walletOutputPort;
        this.transactionService = transactionService;
        this.keycloakUserService = keycloakUserService;
        this.userEntity = userEntity;
        this.userPersistenceMapper = userPersistenceMapper;
        this.passwordEncoder = passwordEncoder;
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
            foundUser.get().setFirstName(user.getFirstName());
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

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        user.setWallet(wallet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        keycloakUserService.createUser(user);
        User savedUser = userOutputPort.saveUser(user); // Output port expects a `User` type
        log.info("User created with wallet in the database");

        return savedUser;
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
