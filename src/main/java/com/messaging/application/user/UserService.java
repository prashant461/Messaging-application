package com.messaging.application.user;

import com.messaging.application.exception.UserAlreadyExistsException;
import com.messaging.application.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsersExcept(String userId) {

        validateUserExists(userId);

        return userRepository.findByIdNot(userId)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User savedUser = userRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(savedUser);
    }

    /** WhatsApp-style presence update */
    public void updateLastSeen(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setLastSeen(LocalDateTime.now());
    }

    private void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }
}

