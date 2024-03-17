package com.zehraarslan.userservice.service.impl;

import com.zehraarslan.userservice.client.ReviewClient;
import com.zehraarslan.userservice.dto.RestaurantSuggestionsDto;
import com.zehraarslan.userservice.dto.UserDto;
import com.zehraarslan.userservice.entity.User;
import com.zehraarslan.userservice.exception.GeneralErrorMessage;
import com.zehraarslan.userservice.exception.SystemException;
import com.zehraarslan.userservice.repository.UserRepository;
import com.zehraarslan.userservice.request.UserSaveRequest;
import com.zehraarslan.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUser_Success() {
        UserSaveRequest request = new UserSaveRequest("Zehra", "Arslan", "zarslan", "za@123", "033453", "za@gmail.com", 41.0082, 28.9784);

        User user = new User();
        user.setId(1L);
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setLatitude(request.latitude());
        user.setLongitude(request.longitude());

        //when(userService.save(User.class)).thenReturn(user);
        UserDto savedUser = userService.save(request);

        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.id());
        assertEquals(user.getName(), savedUser.name());
        assertEquals(user.getEmail(), savedUser.email());
        assertEquals(user.getPhoneNumber(), savedUser.phoneNumber());
        assertEquals(user.getLatitude(), savedUser.latitude());
        assertEquals(user.getLongitude(), savedUser.longitude());
    }



    @Test
    public void testSaveUser_InvalidLocation() {
        UserSaveRequest request = new UserSaveRequest("Zehra", "Arslan", "zarslan", "za@123", "033453", "za@gmail.com", 90.0082, 28.9784);
        assertThrows(SystemException.class, () -> userService.save(request));
    }

    @Test
    void testGetAll_Success() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Zehra");
        user1.setEmail("zehra@example.com");

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user1);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserDto> result = userService.getAll();
        assertEquals(mockUsers.size(), result.size());
    }

    @Test
    void testGetById_Success() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("Zehra");
        mockUser.setEmail("zehra@example.com");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.ofNullable(mockUser));
        UserDto result = userService.getById(userId);

        assertEquals(mockUser.getId(), result.id());
        assertEquals(mockUser.getName(), result.name());
        assertEquals(mockUser.getEmail(), result.email());

    }

  /*  @Test
    public void testGetById_UserNotFound() {

        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> userService.getById(userId));
    }*/
}