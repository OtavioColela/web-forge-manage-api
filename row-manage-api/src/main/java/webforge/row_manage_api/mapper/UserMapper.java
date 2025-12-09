package webforge.row_manage_api.mapper;

import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.model.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequest userRequest){
        return UserEntity.builder()
                .id(userRequest.getId())
                .cpf(userRequest.getCpf())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .password(userRequest.getPassword())
                .school(userRequest.getSchool()).build();
    }

    public static UserResponse toResponse(UserEntity userEntity){
        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .school(userEntity.getSchool()).build();
    }

}

