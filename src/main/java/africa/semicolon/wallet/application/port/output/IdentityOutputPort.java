package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Role;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import org.keycloak.admin.client.resource.UserResource;


public interface IdentityOutputPort {
    void deleteUser(String id);
    User createUser(User user)throws UserNotFoundException;
    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException;
    void assignRole(String userId, Role role);
    UserResource getUserById(String userId);
    void forgetPassword(String username) throws UserNotFoundException;


}

