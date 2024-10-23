package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;

public interface RegisterUserUseCase {

    User createUser(User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException;


}
