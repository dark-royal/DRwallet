package africa.semicolon.wallet.infrastructure.adapter.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

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
    private String name;
    @Column(unique = true)
    private String email;
    @OneToOne(fetch = FetchType.EAGER)
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
