package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {

    UserEntity toUserEntity(User user);
    User toUser(UserEntity user);

}
