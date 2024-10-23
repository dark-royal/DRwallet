package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentDto {

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Currency cannot be null")
    @JsonProperty("currency")
    private String currency;

    @NotNull(message = "Plan cannot be null")
    @JsonProperty("plan")
    private String plan;

}