package com.messaging.application.user;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(isOnline(user.getLastSeen()))
                .build();
    }

    public User toEntity(UserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .lastSeen(LocalDateTime.now())
                .build();
    }

    private boolean isOnline(LocalDateTime lastSeen) {
        return lastSeen != null &&
                lastSeen.isAfter(LocalDateTime.now().minusMinutes(5));
    }
}

