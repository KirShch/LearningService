package com.example.LearningService.service;

import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.Role;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.UserMapper;
import com.example.LearningService.repository.UserRepository;
import com.example.LearningService.exception.UserEmailExistsException;
import com.example.LearningService.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    private User user, user2;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper);

        user = new User();
        user.setId(1L);
        user.setEmail("email@test.t");
        user.setPassword("1234");
        user.setFirstName("A");
        user.setLastName("B");
        user.setRole(Role.ADMIN);
    }


    @Test
    void userRegistration() {
        UserDto userDto = new UserDto("email@test.t", "1234", "A", "B", Role.ADMIN);
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.userRegistration(userDto);

        assertNotNull(result.getId());
        assertEquals("email@test.t", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals("A", result.getFirstName());
        assertEquals("B", result.getLastName());
        assertEquals(Role.ADMIN, result.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findByEmail() {
        String email = "m@m.m";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserEmailExistsException.class, () -> userService.findByEmail(email));
    }

    @Test
    void findById() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void existsById() {
    }

    @Test
    void getAllUsers() {
    }
}