package webforge.row_manage_api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;

import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.mapper.UserMapper;
import webforge.row_manage_api.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static webforge.row_manage_api.validator.UserValidator.validateUser;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public UserResponse createUser(UserRequest userRequest){
        validateUser(userRequest);
        var user = UserMapper.toEntity(userRequest);
        var userResponse = UserMapper.toResponse(user);
        userRepository.saveAndFlush(user);
        return userResponse;
    }

    public List<UserResponse> getAllUsers(){
        if(userRepository.findAll().isEmpty()){
            throw new BadRequestException("Não há usuários no repositorio");
        }
        return userRepository.findAll().stream().map(userEntity -> new UserResponse(userEntity.getName(),
                userEntity.getSchool())).collect(Collectors.toList());
    }

}
