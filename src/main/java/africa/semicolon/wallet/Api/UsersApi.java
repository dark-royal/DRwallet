package africa.semicolon.wallet.Api;

import africa.semicolon.wallet.application.port.input.userUseCases.SendVerificationEmailUseCase;
import africa.semicolon.wallet.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersApi {

    private final UserService authService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) throws UserNotFoundException, WalletAlreadyExistAlreadyException, UserAlreadyExistsException {
        authService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable String id){
        //sendVerificationEmailUseCase.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable String id){
//        authService.(id);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//    }
}
