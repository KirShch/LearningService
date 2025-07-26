package com.example.LearningService.service;

import com.example.LearningService.LearningServiceBuisnessException.UserEmailExistsException;
import com.example.LearningService.LearningServiceBuisnessException.UserNotExistsException;
import com.example.LearningService.dto.UserDto;
import com.example.LearningService.entity.User;
import com.example.LearningService.mapper.UserMapper;
import com.example.LearningService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public User userRegistration(UserDto userDto){
        /*User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreatedAt(Instant.now());*/

        if (existsByEmail(userDto.getEmail())) throw new UserEmailExistsException("User exists with email: " + userDto.getEmail());

        User user = userMapper.toEntity(userDto);
        System.out.println(user);
        user.setCreatedAt(Instant.now());

        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found, email: " + email));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException("User not found, id: " + id));
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
