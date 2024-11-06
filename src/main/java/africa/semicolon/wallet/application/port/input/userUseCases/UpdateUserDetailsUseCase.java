package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.exceptions.PhoneNumberNotFoundException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;

public interface UpdateUserDetailsUseCase {
    User updateUser(User user) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException;
}
