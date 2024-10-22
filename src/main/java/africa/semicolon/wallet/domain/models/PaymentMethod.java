package africa.semicolon.wallet.domain.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentMethod {
    private Long id;
    private Long userId;
    private Type type;
    private String details;
}
