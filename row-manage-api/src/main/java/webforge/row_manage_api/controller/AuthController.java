package webforge.row_manage_api.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import webforge.row_manage_api.config.security.TokenService;
import webforge.row_manage_api.dto.user.UserLoginRequest;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.model.UserEntity;
import webforge.row_manage_api.repository.UserRepository;
import webforge.row_manage_api.service.AuthService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private AuthService authService;
    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, AuthService authService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Login de Usuário",
            description = "Realiza autenticação do usuário com email e senha e retorna um token JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Token retornado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição."),
            @ApiResponse(responseCode = "401", description = "Credenciais incorretas.")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequest user) {
        return ResponseEntity.ok(authService.loginUser(user));
    }

    @GetMapping("/eu")
    public ResponseEntity<UserResponse> eu(Authentication authentication) {
        return ResponseEntity.status(OK).body(authService.getEu(authentication));
    }

    @Operation(summary = "Cria um novo usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de usuário",
                    required = true
            )
            @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(CREATED).body(authService.createUser(userRequest));
    }
}


