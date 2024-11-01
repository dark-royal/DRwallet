package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private Long id;
    private User user;
    private String name;
    private String email;
    private String phoneNumber;
    private Wallet wallet;


}
