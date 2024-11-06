package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;

public interface DeleteUserUseCase {
    void deleteUser(Long id) throws UserNotFoundException;
}
