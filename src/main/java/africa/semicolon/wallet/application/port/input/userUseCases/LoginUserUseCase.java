package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.exceptions.InvalidPasswordException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;

public interface LoginUserUseCase {

    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException, InvalidPasswordException, UserNotFoundException;

}
