package africa.semicolon.wallet.domain.models;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private Date createdOn;
    private String password;
    private Wallet wallet;
    private String phoneNumber;
}
