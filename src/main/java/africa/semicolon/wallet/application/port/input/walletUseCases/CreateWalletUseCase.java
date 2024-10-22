package africa.semicolon.wallet.application.port.input.walletUseCases;

import africa.semicolon.wallet.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.wallet.domain.models.Wallet;

public interface CreateWalletUseCase {
    Wallet createWallet(Wallet wallet) throws WalletAlreadyExistAlreadyException;
}
