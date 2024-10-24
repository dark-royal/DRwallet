package africa.semicolon.wallet;

import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.UserWalletMediator;
import africa.semicolon.wallet.application.service.WalletService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class UserServiceParameterResolver implements ParameterResolver {
    private  UserOutputPort userOutputPort;
    private  UserWalletMediator userWalletMediator;




    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new UserService(userOutputPort,userWalletMediator);
    }
}
