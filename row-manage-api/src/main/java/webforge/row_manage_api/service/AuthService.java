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

        var user = UserMapper.toEntity(userRequest);

        validateFields(user);

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new BadRequestException("Já existe um usuario cadastrado com este email");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.toResponse(userRepository.save(user));
    }

    public String loginUser(UserLoginRequest user) {
        var dbUser = userRepository.findByEmail(user.getEmail());
        System.out.println("SENHA NO BANCO: " + dbUser.getPassword());
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("SENHA: " + user.getPassword());
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return tokenService.generateToken((UserEntity) auth.getPrincipal());

    }
}