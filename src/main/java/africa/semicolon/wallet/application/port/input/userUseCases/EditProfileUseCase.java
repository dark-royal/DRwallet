package africa.semicolon.wallet.application.port.input.userUseCases;

import africa.semicolon.wallet.domain.models.User;

public interface EditProfileUseCase {
    User editProfile(User user);
}
