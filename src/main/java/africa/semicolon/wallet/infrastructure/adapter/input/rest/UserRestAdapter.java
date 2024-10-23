package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.domain.exceptions.IncorrectPaaswordException;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public class UserRestAdapter {

    private final RegisterUserUseCase registerUserUseCase;
    private final EditProfileByNameUseCase editProfileByNameUseCase;
    private final EditProfileByPhoneNumber editProfileByPhoneNumber;
    private final EditProfileByEmailUseCase editProfileByEmailUseCase;
    private final EditProfileByPassword editProfileByPassword ;
    private final GetUserUseCase getUserUseCase;
    private final FindUserByEmailUsesCase findUserByEmailUsesCase;
    private final UserRestMapper userRestMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRestAdapter(RegisterUserUseCase registerUserUseCase, EditProfileByNameUseCase editProfileByNameUseCase, EditProfileByPhoneNumber editProfileByPhoneNumber, EditProfileByEmailUseCase editProfileByEmailUseCase, EditProfileByPassword editProfileByPassword, GetUserUseCase getUserUseCase, FindUserByEmailUsesCase findUserByEmailUsesCase, UserRestMapper userRestMapper, PasswordEncoder passwordEncoder) {
        this.registerUserUseCase = registerUserUseCase;
        this.editProfileByNameUseCase = editProfileByNameUseCase;
        this.editProfileByPhoneNumber = editProfileByPhoneNumber;
        this.editProfileByEmailUseCase = editProfileByEmailUseCase;
        this.editProfileByPassword = editProfileByPassword;
        this.getUserUseCase = getUserUseCase;
        this.findUserByEmailUsesCase = findUserByEmailUsesCase;
        this.userRestMapper = userRestMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        User user = userRestMapper.toUser(createUserRequest);
        createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
        user = registerUserUseCase.createUser(user);
        return  new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
    }


    @PatchMapping("/editName")
    public ResponseEntity<EditProfileResponse> editProfileByName(@RequestBody @Validated final EditProfileRequest editProfileRequest) throws UserAlreadyExistsException, UserNotFoundException {
        User user = userRestMapper.toUser(editProfileRequest);
        user.setName(editProfileRequest.getName());
        user = editProfileByNameUseCase.editProfileByName(user);
        return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
    }

    @PatchMapping("/editPhoneNumber")
    public ResponseEntity<EditProfileResponse> editProfileByPhoneNumber(@RequestBody @Validated final EditProfileRequest editProfileRequest) throws UserAlreadyExistsException, UserNotFoundException {
        User user = userRestMapper.toUser(editProfileRequest);
        user.setPhoneNumber(editProfileRequest.getPhoneNumber());
        user = editProfileByPhoneNumber.editProfileByPhoneNumber(user);
        return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
    }

    @PatchMapping("/editEmail")
    public ResponseEntity<EditProfileResponse> editProfileByEmail(@RequestBody @Validated final EditProfileRequest editProfileRequest) throws UserAlreadyExistsException, UserNotFoundException {
        User user = userRestMapper.toUser(editProfileRequest);
        user.setEmail(editProfileRequest.getEmail());
        user = editProfileByEmailUseCase.editProfileByEmail(user);
        return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
    }

    @PatchMapping("/editPassword")
    public ResponseEntity<EditProfileResponse> editProfileByPassword(@RequestBody @Validated final EditProfileRequest editProfileRequest) throws UserAlreadyExistsException, UserNotFoundException, IncorrectPaaswordException {
        User user = userRestMapper.toUser(editProfileRequest);
        user.setPassword(editProfileRequest.getPhoneNumber());
        user = editProfileByPassword.editProfileByPassword(user);
        return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<FindUserResponse> findUserByEmail(@RequestParam FindUserRequest findUserRequest) throws UserNotFoundException {
        User user = userRestMapper.toUser(findUserRequest);
        user.setEmail(findUserRequest.getEmail());
        user = findUserByEmailUsesCase.findUserByEmail(user.getEmail());
        return new ResponseEntity<>(userRestMapper.toFindUserResponse(user), HttpStatus.OK);

    }

}


