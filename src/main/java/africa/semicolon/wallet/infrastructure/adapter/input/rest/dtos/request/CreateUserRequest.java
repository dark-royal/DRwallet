package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "password must not be empty")
    private String password;
    @NotEmpty(message = "email must not be empty")
    private String email;
    @NotEmpty(message = "phoneNumber must not be empty")
    private String phoneNumber;

}
