package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.models.NewUserRecord;
import africa.semicolon.wallet.domain.models.User;

public interface RegisterKeycloakUserUseCase {
    void createUser(User user);
}
