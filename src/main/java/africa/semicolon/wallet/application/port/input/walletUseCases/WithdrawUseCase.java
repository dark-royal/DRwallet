package africa.semicolon.wallet.application.port.input.walletUseCases;

import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdrawFromWallet(WalletEntity wallet, BigDecimal amount, String accountNumber, String bankCode) throws Exception;

}
