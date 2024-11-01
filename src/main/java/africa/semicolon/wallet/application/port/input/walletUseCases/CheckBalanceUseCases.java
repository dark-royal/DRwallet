package africa.semicolon.wallet.application.port.input.walletUseCases;

import java.math.BigDecimal;

public interface CheckBalanceUseCases {
    BigDecimal getBalance() throws Exception;
}
