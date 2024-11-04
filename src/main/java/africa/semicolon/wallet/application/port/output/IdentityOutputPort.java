package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;

public interface IdentityOutputPort {
    void deleteUser(String id);
    void createUser(User user);
    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException;
}
