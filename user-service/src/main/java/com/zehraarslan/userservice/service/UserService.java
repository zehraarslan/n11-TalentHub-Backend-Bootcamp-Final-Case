package com.zehraarslan.userservice.service;

import com.zehraarslan.userservice.dto.RestaurantDto;
import com.zehraarslan.userservice.dto.RestaurantSuggestionsDto;
import com.zehraarslan.userservice.dto.UserDto;
import com.zehraarslan.userservice.request.UserSaveRequest;
import com.zehraarslan.userservice.request.UserUpdateLocationRequest;
import com.zehraarslan.userservice.request.UserUpdatePasswordRequest;
import com.zehraarslan.userservice.request.UserUpdateRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto save(UserSaveRequest request);
    List<UserDto> saveAll(List<UserSaveRequest> requests);
    UserDto update(Long id, UserUpdateRequest request);
    UserDto updateCustomerPassword(Long id, UserUpdatePasswordRequest request);
    UserDto updateCustomerLocation(Long id, UserUpdateLocationRequest request);
    void deleteUser(Long id);

    List<RestaurantSuggestionsDto> restaurantSuggestions(@PathVariable Long id);
}
