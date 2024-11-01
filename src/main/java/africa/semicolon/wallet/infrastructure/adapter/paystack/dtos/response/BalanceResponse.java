package africa.semicolon.wallet.infrastructure.adapter.paystack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class BalanceResponse {
    private boolean status;
    private String message;
    private Data data;
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Data {
        private BigDecimal balance;


    }
}