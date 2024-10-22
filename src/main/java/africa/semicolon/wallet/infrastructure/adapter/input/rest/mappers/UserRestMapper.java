package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserRestMapper {
    User toUser(CreateUserRequest productCreateRequest);

    CreateUserResponse tocreateUserResponse(User user);



}
