package africa.semicolon.wallet.domain.models;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private Long id;
    private String name;
    private String email;
    @CreatedDate
    private LocalDateTime createdOn;
    private String password;
    private Wallet wallet;
    private String phoneNumber;
}
