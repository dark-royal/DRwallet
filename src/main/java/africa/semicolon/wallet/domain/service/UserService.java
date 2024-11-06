package africa.semicolon.wallet.domain.service;

import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.application.port.output.IdentityOutputPort;
import africa.semicolon.wallet.application.port.output.PremblyOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.exceptions.*;
import africa.semicolon.wallet.domain.models.Transaction;
import africa.semicolon.wallet.domain.models.TransactionDetails;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransactionResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserService implements RegisterUserUseCase, FindUserByEmailUsesCase, GetUserUseCase, LoginUserUseCase , ViewAllTransactionUseCase, UpdateUserDetailsUseCase,DeleteUserUseCase {
    private final UserOutputPort userOutputPort;
    private final KeycloakAdapter keycloakAdapter;
    private final WalletService walletService;
    private final WalletOutputPort walletOutputPort;
    private final TransactionService transactionService;
    private final UserPersistenceMapper userPersistenceMapper;
    private final PasswordEncoder passwordEncoder;
    private final PremblyOutputPort premblyOutputPort;
    private final IdentityOutputPort identityOutputPort;


    public UserService(UserOutputPort userOutputPort, KeycloakAdapter keycloakAdapter, WalletService walletService, WalletOutputPort walletOutputPort, TransactionService transactionService, UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder, PremblyOutputPort premblyOutputPort, IdentityOutputPort identityOutputPort) {
        this.userOutputPort = userOutputPort;
        this.keycloakAdapter = keycloakAdapter;
        this.walletService = walletService;
        this.walletOutputPort = walletOutputPort;
        this.transactionService = transactionService;
        this.userPersistenceMapper = userPersistenceMapper;
        this.passwordEncoder = passwordEncoder;
        this.premblyOutputPort = premblyOutputPort;

        this.identityOutputPort = identityOutputPort;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        verifyUserExistence(user.getEmail());
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        user.setWallet(wallet);
        keycloakAdapter.createUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userOutputPort.saveUser(user);
        log.info("User created with wallet in the database");
        return savedUser;
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
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException {
        User user = userOutputPort.getUserByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("user : {}", user);
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        return keycloakAdapter.loginUser(loginUserRequest);
    }

    private void verifyUserExistence(String email) throws UserAlreadyExistsException {
        boolean existByEmail = userOutputPort.existsByEmail(email);
        if (existByEmail) throw new UserAlreadyExistsException("user exists");
    }

    @Override
    public TransactionResponse viewAllTransactions(Long userId) throws UserNotFoundException {
    if (!userOutputPort.existById(userId)) throw new UserNotFoundException("User not found");

    List<Transaction> transactions = transactionService.getAllTransactionByUserId(userId);

    List<TransactionDetails> transactionDetails = transactions.stream()
        .map(tx -> new TransactionDetails(tx.getId(), tx.getAmount(),tx.getCreatedAt(),tx.getTransactionType()))
        .collect(Collectors.toList());
    return new TransactionResponse(userId, transactionDetails);
}

    @Override
    public User updateUser(User user) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User existinUser = getUserById(user.getId());
        if(!existinUser.getEmail().equals(user.getEmail())){
            throw new UserNotFoundException("user not found");
        }
        if(!existinUser.getPhoneNumber().equals(user.getPhoneNumber())
                && user.getPhoneNumber() != null){
           validatePhoneNumber(existinUser.getPhoneNumber());
            premblyOutputPort.verifyPhoneNumber(user.getPhoneNumber());
        }
        identityOutputPort.editUser(existinUser.getEmail(), user);
        updateUserFields(user,existinUser);
        return userOutputPort.saveUser(existinUser);


    }

    private void validatePhoneNumber(String phoneNumber) throws UserAlreadyExistsException {
        User user = userOutputPort.findByPhoneNumber(phoneNumber);
        if(user != null){
            throw new UserAlreadyExistsException("user exist already");
        }
    }

    private void updateUserFields(User existingUser,User user){
        if(existingUser.getFirstName() != null) user.setFirstName(existingUser.getFirstName());
        if(existingUser.getLastName() != null) user.setLastName(existingUser.getLastName());
        if(existingUser.getPhoneNumber() != null) user.setPhoneNumber(existingUser.getPhoneNumber());
        if(existingUser.getEmail() != null) user.setEmail(existingUser.getEmail());
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userOutputPort.getUserById(id);
        identityOutputPort.deleteUser(user.getEmail());
        userOutputPort.deleteUser(user.getId());
    }
}
