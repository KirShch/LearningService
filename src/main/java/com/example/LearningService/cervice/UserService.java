package com.example.LearningService.cervice;

import com.example.LearningService.dto.UserRegistrationDto;
import com.example.LearningService.dto.UserRegistrationResponseDto;
import com.example.LearningService.model.User;
import com.example.LearningService.repository.UserRepository;
import com.example.LearningService.security.UserDetailsForSecurity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegistrationResponseDto userRegistration(UserRegistrationDto userDto){
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setCreatedAt(LocalDateTime.now());

        return convertUserToResponse(userRepository.save(user));
    }

    public UserRegistrationResponseDto convertUserToResponse(User user){
        UserRegistrationResponseDto userRespDto = new UserRegistrationResponseDto();
        userRespDto.setEmail(user.getEmail());
        userRespDto.setFirstName(user.getFirstName());
        userRespDto.setLastName(user.getLastName());
        userRespDto.setRole(user.getRole());
        return userRespDto;
    }

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found, email: " + email));
    }

    @Transactional
    public User findById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found, email: " + id));
    }

    @Transactional
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDetailsForSecurity getUserDetails(String email){
        return new UserDetailsForSecurity(findByEmail(email));
    }


}
