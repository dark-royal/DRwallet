package africa.semicolon.wallet.infrastructure.adapter;

import africa.semicolon.wallet.application.port.output.IdentityOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.Role;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class KeycloakAdapter implements IdentityOutputPort {

    private static final int STATUS_CREATED = 201;

    private final UserOutputPort userOutputPort;
    private final RestTemplate restTemplate;
    private final Keycloak keycloak;
    private final ObjectMapper objectMapper;

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.tokenUrl}")
    private String tokenUrl;

    public KeycloakAdapter(UserOutputPort userOutputPort, RestTemplate restTemplate, Keycloak keycloak, ObjectMapper objectMapper) {
        this.userOutputPort = userOutputPort;
        this.restTemplate = restTemplate;
        this.keycloak = keycloak;
        this.objectMapper = objectMapper;
    }

    @Override
    public User createUser(User user) throws UserNotFoundException {
        UserRepresentation userRepresentation = createUserRepresentation(user);
        Response response = getUsersResource().create(userRepresentation);
        log.info("Keycloak user creation response status: {}", response.getStatus());

        if (response.getStatus() != STATUS_CREATED) {
            throw new UserNotFoundException("Failed to create user in Keycloak");
        }
        String userId = getUserByUsername(user.getEmail()).getId();
        assignRole(userId, user.getRole());
        user.setKeycloakId(userId);

        return user;
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException {
        try {
            ResponseEntity<String> response = authenticateUserWithKeycloak(loginUserRequest);
            log.info("Response from auth server: {}", response.getBody());
            return objectMapper.readValue(response.getBody(), LoginUserResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing Keycloak response: ", e);
            throw new AuthenticationException("Failed to process login response");
        } catch (HttpClientErrorException e) {
            log.error("Error authenticating user: {}", e.getResponseBodyAsString());
            throw new AuthenticationException("Invalid credentials");
        }
    }

    // Assign Role
    @Override
    public void assignRole(String userId, Role role) {
        UserResource user = getUserById(userId);
        RoleRepresentation roleRepresentation = getRolesResource().get(role.name()).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    @Override
    public void deleteUser(String id) {
        getUsersResource().delete(id);
    }

    private UserRepresentation createUserRepresentation(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setCredentials(List.of(createPasswordCredential(user.getPassword())));
        return userRepresentation;
    }

    private CredentialRepresentation createPasswordCredential(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    private UserRepresentation getUserByUsername(String username) throws UserNotFoundException {
        List<UserRepresentation> users = getUsersResource().search(username);
        if (users.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return users.get(0);
    }

    private ResponseEntity<String> authenticateUserWithKeycloak(LoginUserRequest loginUserRequest) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", loginUserRequest.getEmail());
        params.add("password", loginUserRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(tokenUrl, request, String.class);
    }


    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    public UserResource getUserById(String userId) {
        return getUsersResource().get(userId);
    }

    @Override
    public void forgetPassword(String username) throws UserNotFoundException {
        UsersResource usersResource = getUsersResource();
        UserRepresentation userByUsername = getUserByUsername(username);
        UserResource userResource = usersResource.get(userByUsername.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));

    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }


}
