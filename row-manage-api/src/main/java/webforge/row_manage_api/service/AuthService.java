package webforge.row_manage_api.service;



import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import webforge.row_manage_api.config.security.TokenService;
import webforge.row_manage_api.dto.user.UserLoginRequest;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.exception.ObjectNotFoundException;
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
        if(dbUser == null){
            throw new ObjectNotFoundException("Usuario ou senha incorretos");
        }
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return tokenService.generateToken((UserEntity) auth.getPrincipal());

    }

    public UserResponse getEu(Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getSchool(),
                user.getRole()
        );
    }

}