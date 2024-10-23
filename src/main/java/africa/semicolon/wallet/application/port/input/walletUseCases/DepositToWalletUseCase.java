package africa.semicolon.wallet.application.port.input.walletUseCases;

import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;

import java.math.BigDecimal;

public interface DepositToWalletUseCase {


    void depositToWallet(WalletEntity wallet, BigDecimal amount) throws WalletNotFoundException;
}
