package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.domain.models.Wallet;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.WalletEntity;

public class UserPersistenceMapperImpl implements UserPersistenceMapper {
    @Override
    public UserEntity toUserEntity(User user) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(user.getWallet().getId());
        walletEntity.setBalance(user.getWallet().getBalance());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setRole(user.getRole());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPassword(user.getPassword());
        userEntity.setWallet(walletEntity);

        return userEntity;
    }

    @Override
    public User toUser(UserEntity userEntity) {
        Wallet wallet = new Wallet();
        wallet.setId(userEntity.getWallet().getId());
        wallet.setBalance(userEntity.getWallet().getBalance());

        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setRole(userEntity.getRole());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setLastName(userEntity.getLastName());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setWallet(wallet);

        return user;
    }
}
