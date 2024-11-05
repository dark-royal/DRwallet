package africa.semicolon.wallet.userAdapter;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.domain.exceptions.*;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.service.UserService;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest

@ExtendWith(UserServiceParameterResolver.class)
@RequiredArgsConstructor
public class
UserAdapterTest {
    @Autowired
    private UserService userService;

    @Test
    public void testThatUserCanBeCreated() throws WalletAlreadyExistAlreadyException, UserAlreadyExistsException, UserNotFoundException {
        User user = User
                .builder()
                .email("asa1@gmail.com")
                .firstName("asa")
                .lastName("sk")
                .password("password")
                .createdOn(LocalDateTime.now())
                .phoneNumber("09179832145")
                .build();
        user = userService.createUser(user);
        log.info("user: {}", user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("asa1@gmail.com");
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
    public void testLoginUserSuccess() throws AuthenticationException, UserNotFoundException, InvalidPasswordException {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("asa1@gmail.com");
        loginUserRequest.setPassword("password");
        LoginUserResponse response1 = userService.loginUser(loginUserRequest);
        assertNotNull(response1.getAccessToken());
        assertNotNull(response1.getRefreshToken());

    }

    @Test
    public void testThatUserCanViewAllTransaction(){

    }
}