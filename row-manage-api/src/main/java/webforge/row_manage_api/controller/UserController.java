package webforge.row_manage_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.repository.UserRepository;
import webforge.row_manage_api.service.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.status(CREATED).body(userService.createUser(userRequest));
    }

    @GetMapping("/mostrar")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.status(FOUND).body(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        return ResponseEntity.status(OK).body(userService.updateUser(id, userRequest));
    }

}
