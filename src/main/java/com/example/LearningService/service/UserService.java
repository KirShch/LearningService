package com.example.LearningService.service;

import com.example.LearningService.exception.UserEmailExistsException;
import com.example.LearningService.exception.UserNotFoundException;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.UserMapper;
import com.example.LearningService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User userRegistration(UserDto userDto){
        if (existsByEmail(userDto.getEmail()))
            throw new UserEmailExistsException("User exists with email: " + userDto.getEmail());
        User user = userMapper.toEntity(userDto);
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailExistsException("User not found, email: " + email));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found, id: " + id));
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
