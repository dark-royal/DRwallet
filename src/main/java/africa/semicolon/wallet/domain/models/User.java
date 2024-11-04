package africa.semicolon.wallet.domain.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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
    @CreatedDate
    private LocalDateTime createdOn;
    private String password;
    private String newPassword;

    private Wallet wallet;
    private String phoneNumber;
}
