package africa.semicolon.wallet.infrastructure.adapter.persistence.entities;

import africa.semicolon.wallet.domain.models.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id_id")
    private WalletEntity wallet;
    private String password;
    private String phoneNumber;
    @CreatedDate
//    @Column(name = "created_on", updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
    }
}
