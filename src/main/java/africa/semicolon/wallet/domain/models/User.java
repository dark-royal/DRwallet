package africa.semicolon.wallet.domain.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String bvnNumber;
    private String keycloakId;
    private String role;
    @CreatedDate
    private LocalDateTime createdOn;
    private List<Transaction> transactions;
    private String password;
    private String newPassword;

    private Wallet wallet;
    private String phoneNumber;
}
