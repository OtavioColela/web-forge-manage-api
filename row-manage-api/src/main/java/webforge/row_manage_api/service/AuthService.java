package webforge.row_manage_api.service;



import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webforge.row_manage_api.config.security.TokenService;
import webforge.row_manage_api.dto.user.UserLoginRequest;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.mapper.UserMapper;
import webforge.row_manage_api.model.UserEntity;
import webforge.row_manage_api.repository.UserRepository;

import static webforge.row_manage_api.validator.UserValidator.validateFields;


@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest userRequest) {

        var userEntity = UserMapper.toEntity(userRequest);

        validateFields(userEntity);

        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new BadRequestException("Já existe um usuario cadastrado com este email");
        }

        var encryptedPassword = passwordEncoder.encode(userEntity.getPassword());

        var user = new UserEntity(
                userEntity.getName(),
                userEntity.getCpf(),
                userEntity.getEmail(),
                userEntity.getSchool(),
                encryptedPassword,
                userEntity.getRole()
        );

        return UserMapper.toResponse(userRepository.save(user));
    }

    public String loginUser(UserLoginRequest user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((UserEntity) auth.getPrincipal());

    }
}