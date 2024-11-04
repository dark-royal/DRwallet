package africa.semicolon.wallet.userService;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.service.AuthService;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.domain.exceptions.AuthenticationException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest

@ExtendWith(UserServiceParameterResolver.class)
@RequiredArgsConstructor
public class
    UserServiceTest {
    @Autowired
    private UserService userService;

    @Mock
    private UserOutputPort userOutputPort;

    @Mock
    @Autowired
    private Keycloak keycloakClient;
    @Autowired
    private RestTemplate restTemplate;


    @InjectMocks
    private AuthService authService;


    @Test
    public void testThatUserCanBeCreated() throws WalletAlreadyExistAlreadyException, UserAlreadyExistsException, UserNotFoundException {
        User user = User
                .builder()
                .email("asa@gmail.com")
                .firstName("asa")
                .lastName("sk")
                .password("password")
                .createdOn(LocalDateTime.now())
                .phoneNumber("09189832145")
                .build();
        user = userService.createUser(user);
        log.info("user: {}", user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("asa@gmail.com");
        assertNotNull(user.getWallet());


    }

    @Test
    public void testThatUserCannotRegisterWithTheSameEmail() throws UserNotFoundException, WalletAlreadyExistAlreadyException, UserAlreadyExistsException {
        User user = User
                .builder()
                .email("praise@gmail.com")
                .firstName("Praise")
                .password("password")
                .phoneNumber("09028979349")
                .build();
        user = userService.createUser(user);
        assertThat(user.getId()).isNotNull();
        User finalUser = user;
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(finalUser));
    }

    //@Sql("/db/data.sql")
    @Test
    public void testThatUserCanEditProfileByName() throws UserNotFoundException {
        User user = new User();
        user.setEmail("praise@gmail.com");
        user.setFirstName("Hannah");
        user = userService.editProfileByName(user);
        assertEquals("Hannah", user.getFirstName());


    }

    @Sql("/db/data.sql")
    @Test
    public void testThatUserCanEditProfileByPhoneNumber() throws UserNotFoundException {
        User user = new User();
        user.setEmail("praise@gmail.com");
        user.setPhoneNumber("09187239875");
        user = userService.editProfileByPhoneNumber(user);
        assertEquals("09187239875", user.getPhoneNumber());


    }

    @Test
    public void testLoginUserSuccess() throws  AuthenticationException {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("asa@gmail.com");
        loginUserRequest.setPassword("password");
        LoginUserResponse response1 = userService.loginUser(loginUserRequest);
        assertNotNull(response1.getAccessToken());
        assertNotNull(response1.getRefreshToken());
        //assertNotNull(response1.getExpiresIn());
    }
}