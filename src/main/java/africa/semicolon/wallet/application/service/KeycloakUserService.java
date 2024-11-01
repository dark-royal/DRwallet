package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.DeleteKeycloakUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.ForgotPasswordUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.RegisterKeycloakUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.SendVerificationEmailUseCase;
import africa.semicolon.wallet.domain.models.NewUserRecord;
import africa.semicolon.wallet.domain.models.User;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.tags.form.FormTag;

import java.util.List;
import java.util.Objects;
@Slf4j

public class KeycloakUserService implements RegisterKeycloakUserUseCase, SendVerificationEmailUseCase, DeleteKeycloakUseCase, ForgotPasswordUseCase{
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;

    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public void createUser(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setUsername(user.getEmail());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        log.info("Status code{}", response.getStatus());
        if(Objects.equals(201,response.getStatus())){
            log.info("New user have been created");
        }

    }

    public UsersResource getUsersResource(){
        return keycloak.realm(realm).users();

    }

    @Override
    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void deleteUser(String id) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(id);
    }

    @Override
    public void forgetPassword(String username) {
        List<UserRepresentation> userRepresentations = getUsersResource().searchByUsername(username,true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
    }
}
