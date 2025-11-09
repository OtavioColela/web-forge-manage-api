package webforge.row_manage_api.mapper;

import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.model.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequest userRequest){
        return UserEntity.builder()
                .cpf(userRequest.getCpf())
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .school(userRequest.getSchool()).build();
    }

    public static UserResponse toResponse(UserEntity userEntity){
        return UserResponse.builder()
                .name(userEntity.getName())
                .school(userEntity.getSchool()).build();
    }

    public static UserEntity responseIntoEntity(UserResponse userResponse) {
        return UserEntity.builder()
                .name(userResponse.getName())
                .school(userResponse.getSchool()).build();

    }
}

