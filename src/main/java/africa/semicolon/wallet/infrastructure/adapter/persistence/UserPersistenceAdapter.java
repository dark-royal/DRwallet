package africa.semicolon.wallet.infrastructure.adapter.persistence;

import africa.semicolon.wallet.application.port.output.UserOutputPort;
import africa.semicolon.wallet.domain.exceptions.UserNotFoundException;
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
        UserEntity savedEntity = userRepository.save(userEntity);
        return userPersistenceMapper.toUser(savedEntity);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
         UserEntity userEntity = userRepository.findByEmail(email).get();
         return Optional.of(userPersistenceMapper.toUser(userEntity));
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(userPersistenceMapper::toUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {

        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public UserEntity getUserEntityById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
