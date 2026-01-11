package com.messaging.application.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(userService.getAllUsersExcept(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @PatchMapping("/{userId}/last-seen")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLastSeen(@PathVariable String userId) {
        userService.updateLastSeen(userId);
    }
}

