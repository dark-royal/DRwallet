package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;

public interface WalletPersistenceMapper {
    WalletEntity toWalletEntity(Wallet wallet);
    Wallet toWallet(WalletEntity walletEntity);


}
