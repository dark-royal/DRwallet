package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.output.IdentityOutputPort;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
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

import java.util.List;
import java.util.Objects;
@Slf4j
public class KeycloakAdapter implements IdentityOutputPort {


    private final UserOutputPort userOutputPort;
    private final RestTemplate restTemplate;
    private final Keycloak keycloak;

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.tokenUrl}")
    private String tokenUrl;

    public KeycloakAdapter(UserOutputPort userOutputPort, RestTemplate restTemplate, Keycloak keycloak) {
        this.userOutputPort = userOutputPort;
        this.restTemplate = restTemplate;
        this.keycloak = keycloak;
    }

    @Override
    public void deleteUser(String id) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(id);
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
        credentialRepresentation.setTemporary(false);

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

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", loginUserRequest.getEmail());
        params.add("password", loginUserRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            log.info("Response from auth server: {}", response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), LoginUserResponse.class);
        } catch (JsonProcessingException e) {
            throw new AuthenticationException("Failed to process login response");
        } catch (HttpClientErrorException e) {
            log.info(e.getResponseBodyAsString());
            throw new AuthenticationException("Invalid credentials");
        }
    }
}
