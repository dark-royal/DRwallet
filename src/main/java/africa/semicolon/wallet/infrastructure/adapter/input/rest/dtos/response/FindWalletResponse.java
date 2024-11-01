package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class FindWalletResponse {
    private Long id;
    private BigDecimal balance;
    private Long userId;
}
