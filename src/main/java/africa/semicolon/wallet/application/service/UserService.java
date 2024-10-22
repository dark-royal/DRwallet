package africa.semicolon.wallet.application.service;


import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.IncorrectPaaswordException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.models.User;

import java.util.Optional;

public class UserService implements RegisterUserUseCase, GetUserUseCase, EditProfileByNameUseCase, EditProfileByEmailUseCase, EditProfileByPassword, EditProfileByPhoneNumber {

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
    public User editProfileByName(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if(foundUser.isPresent()){
            foundUser.get().setName(user.getName());
            return foundUser.get();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByEmail(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if(foundUser.isPresent()){
            foundUser.get().setEmail(user.getEmail());
            return foundUser.get();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByPassword(User user) throws IncorrectPaaswordException, UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if(foundUser.isPresent()){
            if(user.getPassword().equals(foundUser.get().getPassword())){
                foundUser.get().setPassword(user.getPassword());
                return foundUser.get();
            }
            else{
                throw new IncorrectPaaswordException("Incorrect Username or password");
            }


            }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User editProfileByPhoneNumber(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if(foundUser.isPresent()){
            foundUser.get().setPhoneNumber(user.getPhoneNumber());
            return foundUser.get();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
