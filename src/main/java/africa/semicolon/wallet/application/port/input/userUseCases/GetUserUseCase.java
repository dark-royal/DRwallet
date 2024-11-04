package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;

public interface GetUserUseCase {
    User getUserById(Long id) throws UserNotFoundException;
}
