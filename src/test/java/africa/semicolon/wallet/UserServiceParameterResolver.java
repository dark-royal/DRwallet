package africa.semicolon.wallet;

import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.KeycloakUserService;
import africa.semicolon.wallet.application.service.TransactionService;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class UserServiceParameterResolver implements ParameterResolver {
    private  UserOutputPort userOutputPort;
    private WalletService walletService;
    private WalletOutputPort walletOutputPort;
    private  TransactionService transactionService;
    private KeycloakUserService keycloakUserService;
    private UserEntity userEntity;
    private UserPersistenceMapper userPersistenceMapper;






    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new UserService(userOutputPort, walletService,walletOutputPort,transactionService,keycloakUserService,userEntity,userPersistenceMapper);
    }
}
