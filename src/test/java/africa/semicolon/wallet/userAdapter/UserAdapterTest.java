package africa.semicolon.wallet.userAdapter;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.domain.exceptions.*;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.service.UserService;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
                .email("cheist4@gmail.com")
                .firstName("ajiri")
                .lastName("ogeh")
                .password("tobi")
                .role("USER")
                .createdOn(LocalDateTime.now())
                .phoneNumber("09038942436")
                .build();
        user = userService.createUser(user);
        log.info("user: {}", user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("cheist4@gmail.com");
        assertNotNull(user.getWallet());


    }

    @Test
    public void testThatUserCannotRegisterWithTheSameEmail() throws UserNotFoundException, WalletAlreadyExistAlreadyException, UserAlreadyExistsException {
        User user = User
                .builder()
                .email("praise@gmail.com")
                .firstName("Praise")
                //.role("ADMIN")
                .password("password")
                .phoneNumber("09028979349")
                .build();
        user = userService.createUser(user);
        assertThat(user.getId()).isNotNull();
        User finalUser = user;
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(finalUser));
    }


    @Test
    public void testLoginUserSuccess() throws AuthenticationException, UserNotFoundException, InvalidPasswordException {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("cheist4@gmail.com");
        loginUserRequest.setPassword("tobi");
        LoginUserResponse response1 = userService.loginUser(loginUserRequest);
        assertNotNull(response1.getAccessToken());
        assertNotNull(response1.getRefreshToken());

    }

    @Test
    public void testThatUserCanViewAllTransaction() throws UserNotFoundException {
        User user = new User();
        user.setEmail("cheist@gmail.com");
        user.setId(2L);
        TransactionResponse responseList = userService.viewAllTransactions(user.getId());
        assertThat(responseList).isNotNull();
        assertThat(responseList).isNotNull();
        assertThat(responseList.getUserId()).isEqualTo(2L);
        assertThat(responseList.getTransactions()).hasSize(1);

    }


    @Test
    public void testThatUserCanEditDetails() throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User user = new User();
        user.setId(4L);
        user.setEmail("cheist4@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        User user1 = userService.udateUser(user);
        assertThat(user1).isNotNull();
    }

    @Test
    public void testThatInvalidUserIdCannotEditProfile() throws UserNotFoundException {
        User user = new User();
        user.setId(6L);
        user.setEmail("cheist4@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        assertThrows(UserNotFoundException.class, () -> userService.udateUser(user));

    }

}