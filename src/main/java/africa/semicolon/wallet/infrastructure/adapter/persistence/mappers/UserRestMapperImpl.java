package africa.semicolon.wallet.infrastructure.adapter.persistence.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers.UserRestMapper;

public class UserRestMapperImpl implements UserRestMapper {
    @Override
    public User toUser(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public CreateUserResponse tocreateUserResponse(User user) {
        return null;
    }

    @Override
    public User toUser(EditProfileRequest editProfileRequest) {
        return null;
    }

    @Override
    public EditProfileResponse toEditProfileResponse(User user) {
        return null;
    }

    @Override
    public User toUser(FindUserRequest findUserRequest) {
        return null;
    }

    @Override
    public FindUserResponse toFindUserResponse(User user) {
        return null;
    }
}
