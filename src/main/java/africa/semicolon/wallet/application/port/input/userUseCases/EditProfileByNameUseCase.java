package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;

public interface EditProfileByNameUseCase {
    User editProfileByName(User user) throws UserNotFoundException;
}