package africa.semicolon.wallet.userService;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.application.service.WalletService;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest

@ExtendWith(UserServiceParameterResolver.class)
@RequiredArgsConstructor
public class UserServiceTest {
    @Autowired
    private  UserService userService;


    @Test
    public void testThatUserCanBeCreated() throws WalletAlreadyExistAlreadyException, UserAlreadyExistsException, UserNotFoundException {
        User user = User
                .builder()
                .email("praise2007@gmail.com")
                .name("Praise")
                .password("password")
                .phoneNumber("09179832145")
                .build();
        user  = userService.createUser(user);
        log.info("user: {}", user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("praise2007@gmail.com");
        assertNotNull(user.getWallet());


    }
    @Test
    public void testThatUserCannotRegisterWithTheSameEmail() throws UserNotFoundException, WalletAlreadyExistAlreadyException, UserAlreadyExistsException {
        User user = User
                .builder()
                .email("praise@gmail.com")
                .name("Praise")
                .password("password")
                .phoneNumber("09028979349")
                .build();
        user  = userService.createUser(user);
        assertThat(user.getId()).isNotNull();
        User finalUser = user;
        assertThrows(UserAlreadyExistsException.class,()->userService.createUser(finalUser));
    }
    @Sql("/db/data.sql")
    @Test
    public void testThatUserCanEditProfileByName() throws UserNotFoundException {
        User user = new User();
        user.setEmail("praise@gmail.com");
        user.setName("Hannah");
        user = userService.editProfileByName(user);
        assertEquals("Hannah", user.getName());


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

}
