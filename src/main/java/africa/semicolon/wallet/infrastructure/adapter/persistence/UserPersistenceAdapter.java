package africa.semicolon.wallet.infrastructure.adapter.persistence;

import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.wallet.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import africa.semicolon.wallet.infrastructure.adapter.persistence.repositories.UserRepository;

import java.util.Optional;

public class UserPersistenceAdapter implements UserOutputPort {
        private final UserRepository userRepository;
        private final UserPersistenceMapper userPersistenceMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        this.userRepository = userRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }


    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> getUserByEmail( final String email) {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.map(userPersistenceMapper::toUser);
    }
}
