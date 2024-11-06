package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.input.userUseCases.*;
import africa.semicolon.wallet.domain.exceptions.*;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.UserRestMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
public class UserRestAdapter {

    private final RegisterUserUseCase registerUserUseCase;

    private final FindUserByEmailUsesCase findUserByEmailUsesCase;
    private final UserRestMapper userRestMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginUserUseCase loginUserUseCase;



    public UserRestAdapter(RegisterUserUseCase registerUserUseCase, RegisterUserUseCase registerUserUseCase1, FindUserByEmailUsesCase findUserByEmailUsesCase, UserRestMapper userRestMapper, PasswordEncoder passwordEncoder, LoginUserUseCase loginUserUseCase){
        this.registerUserUseCase = registerUserUseCase1;
        this.findUserByEmailUsesCase = findUserByEmailUsesCase;
        this.userRestMapper = userRestMapper;
        this.passwordEncoder = passwordEncoder;

        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException, WalletAlreadyExistAlreadyException, UserNotFoundException {
        User user = userRestMapper.toUser(createUserRequest);
        createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
        user = registerUserUseCase.createUser(user);
        return  new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
    }


    @GetMapping("/getUser")
    public ResponseEntity<FindUserResponse> findUserByEmail(@RequestParam FindUserRequest findUserRequest) throws UserNotFoundException {
        User user = userRestMapper.toUser(findUserRequest);
        user.setEmail(findUserRequest.getEmail());
        user = findUserByEmailUsesCase.findUserByEmail(user.getEmail());
        return new ResponseEntity<>(userRestMapper.toFindUserResponse(user), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) throws UserNotFoundException, AuthenticationException, InvalidPasswordException {
        LoginUserResponse loginUserResponse = loginUserUseCase.loginUser(loginUserRequest);
        return new ResponseEntity<>(userRestMapper.toLoginUserResponse(loginUserRequest),HttpStatus.OK);
    }

}


