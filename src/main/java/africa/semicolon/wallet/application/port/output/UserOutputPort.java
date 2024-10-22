package africa.semicolon.wallet.application.port.output;

import africa.semicolon.wallet.domain.models.User;

import java.util.Optional;

public interface UserOutputPort {

    User saveUser(User user);

    Optional<User> getUserByEmail(String email);

}
