package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.*;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Slf4j
public class UserService implements RegisterUserUseCase, EditProfileByNameUseCase, EditProfileByEmailUseCase, EditProfileByPassword, EditProfileByPhoneNumber, FindUserByEmailUsesCase, GetUserUseCase, LoginUserUseCase {

    private final UserOutputPort userOutputPort;
    private final AuthService authService;
    private final WalletService walletService;
    private final WalletOutputPort walletOutputPort;
    private final TransactionService transactionService;
    private final UserPersistenceMapper userPersistenceMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserOutputPort userOutputPort, AuthService authService, WalletService walletService, WalletOutputPort walletOutputPort, TransactionService transactionService,  UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder) {
        this.userOutputPort = userOutputPort;
        this.authService = authService;
        this.walletService = walletService;
        this.walletOutputPort = walletOutputPort;
        this.transactionService = transactionService;
        this.userPersistenceMapper = userPersistenceMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private void verifyUserExistence(String email) throws UserAlreadyExistsException {
        boolean existByEmail = userOutputPort.existsByEmail(email);
        if(existByEmail) throw new UserAlreadyExistsException("user exists");
    }

    @Override
    public User editProfileByName(User user) throws UserNotFoundException {
        User foundUser = userOutputPort.getUserByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        foundUser.setFirstName(user.getFirstName());
        return userOutputPort.saveUser(foundUser);
    }

    @Override
    public User editProfileByEmail(User user) throws UserNotFoundException {
        throw new UnsupportedOperationException("Email editing is not currently supported");
    }

    @Override
    public User editProfileByPassword(User user) throws InvalidPasswordException, UserNotFoundException {
        User foundUser = userOutputPort.getUserByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new InvalidPasswordException("Incorrect current password");
        }
        foundUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
        return userOutputPort.saveUser(foundUser);
    }

    @Override
    public User editProfileByPhoneNumber(User user) throws UserNotFoundException {
        User foundUser = userOutputPort.getUserByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        foundUser.setPhoneNumber(user.getPhoneNumber());
        return userOutputPort.saveUser(foundUser);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userOutputPort.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userOutputPort.getUserById(id);
    }




    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException, AuthenticationException {
        User user = userOutputPort.getUserByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("user : {}",user);
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        return authService.loginUser(loginUserRequest);
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException {
        verifyUserExistence(user.getEmail());
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        user.setWallet(wallet);
        authService.createUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userOutputPort.saveUser(user);
        log.info("User created with wallet in the database");
        return savedUser;
    }
}
