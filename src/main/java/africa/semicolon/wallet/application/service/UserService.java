package africa.semicolon.wallet.application.service;


import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.IncorrectPaaswordException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;


import java.util.Optional;

public class UserService implements RegisterUserUseCase, EditProfileByNameUseCase, EditProfileByEmailUseCase, EditProfileByPassword, EditProfileByPhoneNumber,FindUserByEmailUsesCase {

    private final UserOutputPort userOutputPort;
    private final UserWalletMediator userWalletMediator;




    public UserService(UserOutputPort userOutputPort, UserWalletMediator userWalletMediator) {
        this.userOutputPort = userOutputPort;
        this.userWalletMediator = userWalletMediator;


    }




    private void verifyUserExistence(String email) throws UserAlreadyExistsException, UserNotFoundException {
        User existingUser = findUserByEmail(email);
        if(existingUser != null){
            throw new UserAlreadyExistsException(String.format("%s already exist",email));
        }
    }




    @Override
    public User editProfileByName(User user) throws UserNotFoundException {
        Optional<User> foundUser = userOutputPort.getUserByEmail(user.getEmail());
        if(foundUser.isPresent()){
            foundUser.get().setName(user.getName());
            userOutputPort.saveUser(foundUser.get());
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
            userOutputPort.saveUser(foundUser.get());
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
                userOutputPort.saveUser(foundUser.get());
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
            userOutputPort.saveUser(foundUser.get());
            return foundUser.get();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }



        @Override
        public User createUser (User user) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
            return userWalletMediator.createUserWithWallet(user);
        }


    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
       Optional<User> user = userOutputPort.getUserByEmail(email);
       if(user.isPresent()){
           return user.get();
       }
       else{
           throw new UserNotFoundException("user not found");
       }
    }
}
