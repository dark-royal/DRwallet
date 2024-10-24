package africa.semicolon.wallet.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Payment {
    private Long id;
    private User userId;
    private BigDecimal amount;
    private String currency;
    private Status status;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;

}
