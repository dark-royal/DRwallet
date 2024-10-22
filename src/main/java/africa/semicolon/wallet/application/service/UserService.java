package africa.semicolon.wallet.application.service;

import africa.semicolon.wallet.application.port.input.userUseCases.EditProfileUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.FindUserByEmailUsesCase;
import africa.semicolon.wallet.application.port.input.userUseCases.GetUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.RegisterUserUseCase;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;

import java.util.Optional;

public class UserService implements RegisterUserUseCase, GetUserUseCase, EditProfileUseCase {

    private final UserOutputPort userOutputPort;
    private final FindUserByEmailUsesCase findUserByEmailUsesCase;

    public UserService(UserOutputPort userOutputPort, FindUserByEmailUsesCase findUserByEmailUsesCase) {
        this.userOutputPort = userOutputPort;
        this.findUserByEmailUsesCase = findUserByEmailUsesCase;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        verifyUserExistence(user.getEmail());
        user = userOutputPort.saveUser(user);
        return user;
    }

    private void verifyUserExistence(String email) throws UserAlreadyExistsException {
        User existingUser = findUserByEmailUsesCase.findUserByEmail(email);
        if(existingUser != null){
            throw new UserAlreadyExistsException(String.format("%s already exist",email));
        }
    }


    @Override
    public User getUserById(String email) throws UserNotFoundException {
        Optional<User> user = userOutputPort.getUserByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        else{
            throw new UserNotFoundException("User not found");
        }

    }

    @Override
    public User editProfile(User user) {
        return null;
    }
}
