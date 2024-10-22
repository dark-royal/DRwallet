package africa.semicolon.wallet.infrastructure.adapter.input.rest;

import africa.semicolon.wallet.application.port.input.userUseCases.GetUserUseCase;
import africa.semicolon.wallet.application.port.input.userUseCases.RegisterUserUseCase;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserRestAdapter {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserRestMapper userRestMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRestAdapter(RegisterUserUseCase registerUserUseCase, GetUserUseCase getUserUseCase, UserRestMapper userRestMapper, PasswordEncoder passwordEncoder) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.userRestMapper = userRestMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        User user = userRestMapper.toUser(createUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = registerUserUseCase.createUser(user);
        return  new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
    }
}


