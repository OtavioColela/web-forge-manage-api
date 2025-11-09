package webforge.row_manage_api;

import org.hibernate.mapping.Any;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.model.UserEntity;
import webforge.row_manage_api.repository.UserRepository;
import webforge.row_manage_api.service.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    UserEntity userEntity;
    UserRequest userRequest;

    @Before
    public void setup(){
        userEntity = new UserEntity(1L, "Otavio", "COMAP", "2", "Walley123_");
        userRequest = new UserRequest(1L, "Otavio", "COMAP", "2", "Walley123_");

    }

    @Test
    public void mustCreateUser(){
        when(userRepository.saveAndFlush(any(UserEntity.class))).thenReturn(userEntity);
        var result = userService.createUser(userRequest);
        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
        assertEquals("Otavio", result.getName());
        assertEquals("COMAP", result.getSchool());
    }

    @Test
    public void mustFailFindingUsers(){
        given(userRepository.findAll()).willReturn(List.of());
        assertThrows(BadRequestException.class, () -> {
            userService.getAllUsers();
        });
    }

    @Test
    public void mustGetAllUsers(){
        var users = List.of(userEntity, userEntity);
        given(userRepository.findAll()).willReturn(users);
        var result = userService.getAllUsers();

        assertEquals("Otavio", result.get(0).getName());
        assertEquals("COMAP", result.get(0).getSchool());
        assertEquals("Otavio", result.get(1).getName());
        assertEquals("COMAP", result.get(1).getSchool());
    }


}
