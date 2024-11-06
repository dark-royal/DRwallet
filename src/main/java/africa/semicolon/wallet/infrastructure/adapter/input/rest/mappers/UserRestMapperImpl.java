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

import java.time.LocalDateTime;

public class UserRestMapperImpl implements UserRestMapper {
    @Override
    public User toUser(CreateUserRequest createUserRequest) {
       User user = new User();
       user.setFirstName(createUserRequest.getName());
       user.setEmail(createUserRequest.getEmail());
       user.setPassword(createUserRequest.getPassword());
       user.setPhoneNumber(createUserRequest.getPhoneNumber());
       user.setWallet(createUserRequest.getWallet());
       user.setCreatedOn(LocalDateTime.now());
        return user;
    }

    @Override
    public CreateUserResponse tocreateUserResponse(User user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(user.getFirstName());
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setPhoneNumber(user.getPhoneNumber());
        createUserResponse.setId(user.getId());
        createUserResponse.setWallet(user.getWallet());
        return createUserResponse;
    }

    @Override
    public User toUser(EditProfileRequest editProfileRequest) {
        User user = new User();
        user.setFirstName(editProfileRequest.getFirstName());
        user.setEmail(editProfileRequest.getEmail());
        user.setLastName(editProfileRequest.getLastName());
        user.setPhoneNumber(editProfileRequest.getPhoneNumber());
        return user;
    }

    @Override
    public EditProfileResponse toEditProfileResponse(User user) {
        EditProfileResponse editProfileResponse = new EditProfileResponse();
        editProfileResponse.setId(user.getId());
        editProfileResponse.setFirstName(user.getFirstName());
        editProfileResponse.setLastName(user.getLastName());
        editProfileResponse.setEmail(user.getEmail());
        editProfileResponse.setPhoneNumber(user.getPhoneNumber());

        return editProfileResponse;
    }

    @Override
    public User toUser(FindUserRequest findUserRequest) {
        User user = new User();
        user.setEmail(findUserRequest.getEmail());
        return user;
    }

    @Override
    public FindUserResponse toFindUserResponse(User user) {
        FindUserResponse findUserResponse = new FindUserResponse();
        findUserResponse.setId(user.getId());
        findUserResponse.setName(user.getFirstName());
        findUserResponse.setEmail(user.getEmail());
        findUserResponse.setPhoneNumber(user.getPhoneNumber());
        return findUserResponse;
    }

    @Override
    public LoginUserResponse toLoginUserResponse(LoginUserRequest loginUserRequest) {
        return null;
    }
}
