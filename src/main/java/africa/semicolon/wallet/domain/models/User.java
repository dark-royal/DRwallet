package africa.semicolon.wallet.domain.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nin;
    private String phoneNumber;
}
