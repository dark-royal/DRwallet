package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.models.User;

public interface RegisterUserUseCase {

    User createUser(User user) throws UserAlreadyExistsException;


}
