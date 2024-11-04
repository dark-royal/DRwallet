package africa.semicolon.wallet;

import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.*;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceParameterResolver implements ParameterResolver {
    private UserOutputPort userOutputPort;
    private AuthService authService;
    private WalletService walletService;
    private WalletOutputPort walletOutputPort;
    private TransactionService transactionService;
    private UserPersistenceMapper userPersistenceMapper;
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new UserService(userOutputPort,authService, walletService, walletOutputPort, transactionService, userPersistenceMapper, passwordEncoder);
    }
}