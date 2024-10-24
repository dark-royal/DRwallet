package africa.semicolon.wallet.userService;

import africa.semicolon.wallet.UserServiceParameterResolver;
import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(UserServiceParameterResolver.class)
public class UserServiceTest {
    private final UserService userService;

    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testThatUserCanBeCreated() throws WalletAlreadyExistAlreadyException, UserAlreadyExistsException, UserNotFoundException {
        User user = User
                .builder()
                .email("praise@gmail.com")
                .name("Praise")
                .password("password")
                .phoneNumber("09028979349")
                .build();
        user  = userService.createUser(user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("praise@gmail.com");
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
        user = userService.editProfileByName(user);
        assertEquals("09187239875", user.getPhoneNumber());


    }

}
