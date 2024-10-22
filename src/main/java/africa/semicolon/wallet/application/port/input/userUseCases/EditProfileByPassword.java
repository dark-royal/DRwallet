package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.IncorrectPaaswordException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;

public interface EditProfileByPassword {
    User editProfileByPassword(User user) throws IncorrectPaaswordException, UserNotFoundException;
}
