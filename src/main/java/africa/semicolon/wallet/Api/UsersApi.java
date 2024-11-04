package africa.semicolon.wallet.Api;

import africa.semicolon.wallet.application.service.AuthService;
import africa.semicolon.wallet.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersApi {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user){
        authService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
//
//    @PutMapping("/{id}send-verification-email")
//    public ResponseEntity<?> sendVerificationEmail(@PathVariable String id){
//        authService.sendVerificationEmail(id);
//        return ResponseEntity.status(HttpStatus.OK).build();
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        authService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
