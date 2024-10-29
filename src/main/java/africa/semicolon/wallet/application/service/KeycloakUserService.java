package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.RegisterKeycloakUserUseCase;
import africa.semicolon.wallet.domain.models.NewUserRecord;
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

import java.util.List;
import java.util.Objects;
@Slf4j

public class KeycloakUserService implements RegisterKeycloakUserUseCase {
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;

    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public void createUser(NewUserRecord newUserRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.firstName());
        userRepresentation.setEmail(newUserRecord.email());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setUsername(newUserRecord.username());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newUserRecord.password());
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        log.info("Status code{}", response.getStatus());
        if(Objects.equals(201,response.getStatus())){
            log.info("New user have been created");
        }

    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();

    }
}
