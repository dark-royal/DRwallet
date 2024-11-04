package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.DeleteUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.LoginUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.RegisterKeycloakUserUseCase;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.KeycloakAdapter;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AuthService implements RegisterKeycloakUserUseCase, LoginUserUseCase, DeleteUserUseCase {

   private final KeycloakAdapter identityManager;

    public AuthService(KeycloakAdapter identityManager) {
        this.identityManager = identityManager;
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginRequest) throws AuthenticationException {
       return identityManager.loginUser(loginRequest);
    }


    @Override
    public void createUser(User user) {
        identityManager.createUser(user);
    }



    @Override
    public void deleteUser(String id) {
      identityManager.deleteUser(id);
    }


    }




