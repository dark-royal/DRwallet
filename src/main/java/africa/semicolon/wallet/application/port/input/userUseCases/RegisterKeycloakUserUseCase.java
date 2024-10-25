package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.models.NewUserRecord;

public interface RegisterKeycloakUserUseCase {
    void createUser(NewUserRecord newUserRecord);
}
