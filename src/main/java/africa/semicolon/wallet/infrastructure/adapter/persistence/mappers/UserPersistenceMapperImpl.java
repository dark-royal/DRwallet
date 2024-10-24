package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;

public class UserPersistenceMapperImpl implements UserPersistenceMapper {
    @Override
    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .phoneNumber(userEntity.getPhoneNumber())
                .email(userEntity.getEmail())
                .build();
    }
}
