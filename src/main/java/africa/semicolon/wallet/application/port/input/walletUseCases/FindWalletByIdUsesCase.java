package africa.semicolon.wallet.application.port.input.walletUseCases;

import africa.semicolon.wallet.domain.exceptions.WalletNotFoundException;
import africa.semicolon.wallet.domain.models.Wallet;

public interface FindWalletByIdUsesCase {
    Wallet findWalletById(Long id) throws WalletNotFoundException;


}
