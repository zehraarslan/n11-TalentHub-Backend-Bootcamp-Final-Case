package com.zehraarslan.userservice.controller;

import com.zehraarslan.userservice.dto.UserDto;
import com.zehraarslan.userservice.general.RestResponse;
import com.zehraarslan.userservice.request.UserSaveRequest;
import com.zehraarslan.userservice.request.UserUpdateLocationRequest;
import com.zehraarslan.userservice.request.UserUpdatePasswordRequest;
import com.zehraarslan.userservice.request.UserUpdateRequest;
import com.zehraarslan.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    public ResponseEntity<RestResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getById(id);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users")
    public ResponseEntity<RestResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtos = userService.getAll();
        return ResponseEntity.ok(RestResponse.of(userDtos));
    }

    @PostMapping
    @Operation(summary = "Save user", description = "Creates a new user")
    public ResponseEntity<RestResponse<UserDto>> saveUser(@RequestBody @Valid  UserSaveRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.save(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates a user by their ID")
    public ResponseEntity<RestResponse<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.update(id, request)));
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Update user password", description = "Updates a user's password by their ID")
    public ResponseEntity<RestResponse<UserDto>> updatePassword(@PathVariable Long id, @Valid @RequestBody UserUpdatePasswordRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.updateCustomerPassword(id, request)));
    }

    @PatchMapping("/{id}/location")
    @Operation(summary = "Update user location", description = "Updates a user's location by their ID")
    public ResponseEntity<RestResponse<UserDto>> updateLocation(@PathVariable Long id, @Valid @RequestBody UserUpdateLocationRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.updateCustomerLocation(id, request)));
    }
}
