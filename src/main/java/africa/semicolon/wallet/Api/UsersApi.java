package africa.semicolon.wallet.Api;

import africa.semicolon.wallet.application.service.UserService;
import africa.semicolon.wallet.domain.models.NewUserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersApi {

    private final UserService userService;


    public ResponseEntity<?> createUser(@RequestBody NewUserRecord newUserRecord){
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
