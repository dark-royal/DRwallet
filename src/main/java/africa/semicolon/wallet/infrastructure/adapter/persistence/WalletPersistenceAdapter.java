package africa.semicolon.wallet.infrastructure.adapter.persistence;

import africa.semicolon.wallet.application.port.output.WalletOutputPort;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.WalletPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.WalletRepository;

import java.util.Optional;

public class WalletPersistenceAdapter implements WalletOutputPort {
    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    public WalletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        this.walletRepository = walletRepository;
        this.walletPersistenceMapper = walletPersistenceMapper;
    }


    @Override
    public Wallet saveWallet(Wallet wallet) {
        WalletEntity walletEntity = walletPersistenceMapper.toWalletEntity(wallet);
        walletEntity = walletRepository.save(walletEntity);
        return walletPersistenceMapper.toWallet(walletEntity);
    }

    @Override
    public Optional<Wallet> getWalletById(Long id) {
        final Optional<WalletEntity> walletEntity = walletRepository.findById(id);
        return walletEntity.map(walletPersistenceMapper::toWallet);
    }
}
