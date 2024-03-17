package com.zehraarslan.userservice.service.impl;

import com.zehraarslan.userservice.client.ReviewClient;
import com.zehraarslan.userservice.dto.RestaurantDto;
import com.zehraarslan.userservice.dto.RestaurantSuggestionsDto;
import com.zehraarslan.userservice.dto.UserDto;
import com.zehraarslan.userservice.entity.User;
import com.zehraarslan.userservice.exception.GeneralErrorMessage;
import com.zehraarslan.userservice.exception.SystemException;
import com.zehraarslan.userservice.exception.UserErrorMessage;
import com.zehraarslan.userservice.general.BaseAddiionalsFields;
import com.zehraarslan.userservice.general.RestResponse;
import com.zehraarslan.userservice.mapper.RestaurantMapper;
import com.zehraarslan.userservice.mapper.UserMapper;
import com.zehraarslan.userservice.repository.UserRepository;
import com.zehraarslan.userservice.request.UserSaveRequest;
import com.zehraarslan.userservice.request.UserUpdateLocationRequest;
import com.zehraarslan.userservice.request.UserUpdatePasswordRequest;
import com.zehraarslan.userservice.request.UserUpdateRequest;
import com.zehraarslan.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReviewClient reviewClient;
    private static final double EARTH_RADIUS = 6371.0;


    @Override
    public List<UserDto> getAll() {
        try {
            log.info("Retrieving all users");
            List<User> users = userRepository.findAll();
            List<UserDto> userDtos = UserMapper.INSTANCE.convertToUserDtos(users);
            log.info("Retrieved all users successfully");
            return userDtos;
        } catch (Exception e) {
            log.error("Error occurred while retrieving all users: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto getById(Long id) {
        try {
            log.info("Retrieving user by id: {}", id);
            User user = getUserByIdIfExists(id);
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
            log.info("User retrieved successfully: {}", user.getId());
            return userDto;
        } catch (Exception e) {
            log.error("Error occurred while retrieving user by id {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto save(UserSaveRequest request) {
        try {
            log.info("Saving user with request: {}", request);
            checkUniqueness(request.name(), request.email(), request.phoneNumber(), null);
            validateLocation(request.latitude(), request.longitude());
            User user = UserMapper.INSTANCE.convertToUser(request);
            setBaseAdditionalFields(user);
            user = userRepository.save(user);
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
            log.info("User saved successfully: {}", user.getId());
            return userDto;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving user: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserDto> saveAll(List<UserSaveRequest> requests) {
        try {
            log.info("Saving multiple users");
            List<User> users = requests.stream()
                    .map(request -> {
                        checkUniqueness(request.name(), request.email(), request.phoneNumber(), null);
                        validateLocation(request.latitude(), request.longitude());
                        User user = UserMapper.INSTANCE.convertToUser(request);
                        setBaseAdditionalFields(user);
                        return user;
                    })
                    .collect(Collectors.toList());
            users = userRepository.saveAll(users);
            return users.stream()
                    .map(user -> UserMapper.INSTANCE.convertToUserDto(user))
                    .collect(Collectors.toList());
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving multiple users: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto update(Long id, UserUpdateRequest request) {
        try {
            User user = getUserByIdIfExists(id);

            user.setName(request.name().isBlank() ?  user.getName() : request.name());
            user.setSurname(request.surname().isBlank() ? request.surname() : user.getSurname());
            user.setUserName(request.userName().isBlank() ? request.userName() :  user.getUserName());
            user.setEmail(request.email().isBlank() ? request.email() : user.getEmail());
            user.setPhoneNumber(request.phoneNumber().isBlank() ? request.phoneNumber() : user.getPhoneNumber());

            checkUniqueness(user.getUserName(), user.getEmail(), user.getPhoneNumber(), id);
            setBaseAdditionalFields(user);
            user = userRepository.save(user);
            log.info("User updated successfully: {}", user.getId());
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
            return userDto;
        } catch (Exception e) {
            log.error("Error occurred while updating user {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto updateCustomerPassword(Long id, UserUpdatePasswordRequest request) {
        try {
            User user = getUserByIdIfExists(id);

            if(!user.getPassword().equals(request.currentPassword())) {
                throw new SystemException(UserErrorMessage.INVALID_OLD_PASSWORD);
            }
            if(!request.newPassword().equals(request.confirmNewPassword())) {
                throw new SystemException(UserErrorMessage.NEW_PASSWORDS_DID_NOT_MATCH);
            }
            user.setPassword(request.newPassword());
            setBaseAdditionalFields(user);
            user = userRepository.save(user);
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
            log.info("User password updated successfully: {}", user.getId());
            return userDto;
        } catch (Exception e) {
            log.error("Error occurred while updating user password {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDto updateCustomerLocation(Long id, UserUpdateLocationRequest request) {
        try {
            User user = getUserByIdIfExists(id);
            validateLocation(request.latitude(), request.longitude());
            user.setLatitude(request.latitude());
            user.setLongitude(request.longitude());
            setBaseAdditionalFields(user);
            user = userRepository.save(user);
            UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
            log.info("User location updated successfully: {}", user.getId());
            return userDto;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user location {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            User user = getUserByIdIfExists(id);
            userRepository.delete(user);
            log.info("User deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting user {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RestaurantSuggestionsDto> restaurantSuggestions(Long id) {
        try {
            User user = getUserByIdIfExists(id);
            double userLat = user.getLatitude();
            double userLng = user.getLongitude();

            List<RestaurantDto> restaurantDtos = reviewClient.getAllRestaurants().getBody().getData();
            List<RestaurantSuggestionsDto> nearbyRestaurants = new ArrayList<>();

            for (RestaurantDto restaurant : restaurantDtos) {
                double restaurantLat = restaurant.latitude();
                double restaurantLng = restaurant.longitude();

                double distance = calculateDistance(userLat, userLng, restaurantLat, restaurantLng);
                if (distance <= 10) {
                    double totalScore = (0.7 * restaurant.reviewScore()) + (0.3 * distance);
                    nearbyRestaurants.add(RestaurantMapper.INSTANCE.convertToRestauran(restaurant, totalScore));
                }
            }
            Collections.sort(nearbyRestaurants, Comparator.comparingDouble(RestaurantSuggestionsDto::recommendationScore).reversed());
            return nearbyRestaurants.stream().limit(3).collect(Collectors.toList());

        } catch (Exception e) {
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    public static double calculateDistance(double userLat, double userLng, double restaurantLat, double restaurantLng) {
        double userLatRad = Math.toRadians(userLat);
        double userLngRad = Math.toRadians(userLng);
        double restaurantLatRad = Math.toRadians(restaurantLat);
        double restaurantLngRad = Math.toRadians(restaurantLng);

        double dLat = restaurantLatRad - userLatRad;
        double dLng = restaurantLngRad - userLngRad;


        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(userLatRad) * Math.cos(restaurantLatRad) * Math.pow(Math.sin(dLng / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }


    private User getUserByIdIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new SystemException(GeneralErrorMessage.ITEM_NOT_FOUND));
    }

    private void setBaseAdditionalFields(User user) {
        BaseAddiionalsFields baseAddiionalsFields = user.getBaseAddiionalsFields();
        if (baseAddiionalsFields == null) {
            baseAddiionalsFields = new BaseAddiionalsFields();
        }
        LocalDateTime now = LocalDateTime.now();
        if (user.getId() == null) {
            baseAddiionalsFields.setCreateDate(now);
        }
        baseAddiionalsFields.setUpdateDate(now);
        user.setBaseAddiionalsFields(baseAddiionalsFields);
    }

    private void checkUniqueness(String userName, String email, String phoneNumber, Long id) {
        if (userName.isBlank() && userRepository.existsUserByUserNameAndIdNot(userName, id)) {
            throw new SystemException(UserErrorMessage.DUPLICATE_USERNAME);
        }
        if (email.isBlank() && userRepository.existsUserByEmailAndIdNot(email, id)) {
            throw new SystemException(UserErrorMessage.DUPLICATE_EMAIL);
        }
        if (phoneNumber.isBlank() && userRepository.existsUserByPhoneNumberAndIdNot(phoneNumber, id)) {
            throw new SystemException(UserErrorMessage.DUPLICATE_PHONE_NUMBER);
        }
    }

    private void validateLocation(Double latitude, Double longitude) {
        if (latitude == null || (latitude < -90 || latitude > 90)) {
            throw new SystemException(UserErrorMessage.INVALID_LATITUDE);
        }
        if (longitude == null || (longitude < -180 || longitude > 180)) {
            throw new SystemException(UserErrorMessage.INVALID_LONGITUDE);
        }
    }
}
