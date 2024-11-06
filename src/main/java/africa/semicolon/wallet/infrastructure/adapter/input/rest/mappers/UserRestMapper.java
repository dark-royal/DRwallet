package africa.semicolon.wallet.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.wallet.domain.models.User;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatusCode;


public interface UserRestMapper {
    User toUser(CreateUserRequest createUserRequest);

    CreateUserResponse tocreateUserResponse(User user);

    User toUser(EditProfileRequest editProfileRequest);
    EditProfileResponse toEditProfileResponse(User user);

    User toUser(FindUserRequest findUserRequest);
    FindUserResponse toFindUserResponse(User user);

    LoginUserResponse toLoginUserResponse(LoginUserRequest loginUserRequest);





}
