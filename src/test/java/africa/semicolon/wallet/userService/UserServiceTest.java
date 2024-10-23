package africa.semicolon.wallet.userService;

import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    private final UserService userService;

    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testThatUserCanBeCreated() throws WalletAlreadyExistAlreadyException, UserAlreadyExistsException {
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
}
