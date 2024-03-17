package com.zehraarslan.restaurantservice.service.impl;

import com.zehraarslan.restaurantservice.dto.RestaurantDto;
import com.zehraarslan.restaurantservice.entity.Restaurant;
import com.zehraarslan.restaurantservice.exception.GeneralErrorMessage;
import com.zehraarslan.restaurantservice.exception.SystemException;
import com.zehraarslan.restaurantservice.mapper.RestaurantMapper;
import com.zehraarslan.restaurantservice.repository.RestaurantRepository;
import com.zehraarslan.restaurantservice.request.RestaurantSaveRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateLocationRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdatePasswordRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateRequest;
import com.zehraarslan.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantDto> getAll() {
        try {
            log.info("Retrieving all restaurants");
            List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
            List<RestaurantDto> restaurantDtos = RestaurantMapper.INSTANCE.convertToRestaurantDtos(restaurants);
            log.info("Retrieved all restaurants successfully");
            return RestaurantMapper.INSTANCE.convertToRestaurantDtos(restaurants);
        } catch (Exception e) {
            log.error("Error occurred while retrieving all restaurants: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDto getById(String id) {
        try {
            log.info("Retrieving user by id: {}", id);
            Restaurant restaurant = getRestaurantByIdIfExists(id);
            RestaurantDto restaurantDto = RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant);
            log.info("Restaurant retrieved successfully: {}", restaurant.getId());
            return restaurantDto;
        } catch (Exception e) {
            log.error("Error occurred while retrieving restaurant by id {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDto save(RestaurantSaveRequest request) {
        try {
            log.info("Saving restaurant with request: {}", request);

            if (request.name() == null || request.email() == null || request.phoneNumber() == null || request.description() == null ||
                    request.latitude() == null || request.longitude() == null) {
                throw new SystemException(GeneralErrorMessage.ILLEGAL_ARGUMENT_EXCEPTION);
            }
            checkUniqueness(request.email(), request.phoneNumber(), null);
            validateLocation(request.latitude(), request.longitude());
            Restaurant restaurant = RestaurantMapper.INSTANCE.convertToRestaurant(request);
            setBaseAdditionalFields(restaurant);
            restaurantRepository.save(restaurant);
            RestaurantDto restaurantDto = RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant);
            log.info("Restaurant saved successfully: {}", restaurant.getId());
            return restaurantDto;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving restaurant: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RestaurantDto> saveAll(List<RestaurantSaveRequest> requests) {
        try {
            log.info("Saving multiple restaurants");
            List<Restaurant> restaurants = requests.stream()
                    .map(request -> {
                        if (request.name() == null || request.email() == null || request.phoneNumber() == null || request.description() == null ||
                                request.latitude() == null || request.longitude() == null) {
                            throw new SystemException(GeneralErrorMessage.ILLEGAL_ARGUMENT_EXCEPTION);
                        }
                        checkUniqueness(request.email(), request.phoneNumber(), null);
                        validateLocation(request.latitude(), request.longitude());
                        Restaurant restaurant = RestaurantMapper.INSTANCE.convertToRestaurant(request);
                        setBaseAdditionalFields(restaurant);
                        return restaurant;
                    })
                    .collect(Collectors.toList());
            restaurantRepository.saveAll(restaurants);
            return restaurants.stream()
                    .map(restaurant -> RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant))
                    .collect(Collectors.toList());
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while saving multiple restaurants: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public RestaurantDto update(String  id, RestaurantUpdateRequest request) {
            try
            {
                if (request.name() == null || request.email() == null || request.phoneNumber() == null || request.description() == null) {
                    throw new SystemException(GeneralErrorMessage.ILLEGAL_ARGUMENT_EXCEPTION);
                }
                Restaurant restaurant = getRestaurantByIdIfExists(id);
                restaurant.setName(request.name() == null ?  restaurant.getName() : request.name());
                restaurant.setEmail(request.email() == null ? restaurant.getEmail() : request.email());
                restaurant.setPhoneNumber(request.phoneNumber() == null ? restaurant.getPhoneNumber() : request.phoneNumber());

                checkUniqueness(restaurant.getEmail(), restaurant.getPhoneNumber(), id);
                setBaseAdditionalFields(restaurant);
                restaurant = restaurantRepository.save(restaurant);
                log.info("restaurant updated successfully: {}", restaurant.getId());
                return RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant);
            } catch (Exception e) {
                log.error("Error occurred while updating restaurant {}: {}", id, e.getMessage());
                throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
            }
    }

    @Override
    public RestaurantDto updateCustomerLocation(String id, RestaurantUpdateLocationRequest request) {
        try {
            if (request.latitude() == null || request.longitude() == null) {
                throw new SystemException(GeneralErrorMessage.ILLEGAL_ARGUMENT_EXCEPTION);
            }
            Restaurant restaurant = getRestaurantByIdIfExists(id);
            validateLocation(request.latitude(), request.longitude());
            restaurant.setLatitude(request.latitude());
            restaurant.setLongitude(request.longitude());
            setBaseAdditionalFields(restaurant);
            restaurant = restaurantRepository.save(restaurant);
            RestaurantDto restaurantDto = RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant);
            log.info("User location updated successfully: {}", restaurant.getId());
            return restaurantDto;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating restaurant location {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDto updateCustomerPassword(String id, RestaurantUpdatePasswordRequest request) {
        try {
            if (request.currentPassword() == null || request.newPassword() == null || request.confirmNewPassword() == null) {
                throw new SystemException(GeneralErrorMessage.ILLEGAL_ARGUMENT_EXCEPTION);
            }
            Restaurant restaurant = getRestaurantByIdIfExists(id);
            if(!restaurant.getPassword().equals(request.currentPassword())) {
                throw new SystemException(GeneralErrorMessage.INVALID_OLD_PASSWORD);
            }
            if(!request.newPassword().equals(request.confirmNewPassword())) {
                throw new SystemException(GeneralErrorMessage.NEW_PASSWORDS_DID_NOT_MATCH);
            }
            restaurant.setPassword(request.newPassword());
            setBaseAdditionalFields(restaurant);
            restaurant = restaurantRepository.save(restaurant);
            RestaurantDto restaurantDto = RestaurantMapper.INSTANCE.convertToRestaurantDto(restaurant);
            log.info("Restaurant password updated successfully: {}", restaurant.getId());
            return restaurantDto;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating restaurant password {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteRestaurant(String id) {
        try {
            Restaurant restaurant = getRestaurantByIdIfExists(id);
            restaurantRepository.delete(restaurant);
            log.info("Restaurant deleted successfully: {}", id);
        } catch (SystemException e) {
            log.error("Error occurred while deleting user {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting user {}: {}", id, e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteAllRestaurant() {
        restaurantRepository.deleteAll();
    }

    @Override
    public String updateReviewScore(String id, Integer score) {
        try {
            Restaurant restaurant = getRestaurantByIdIfExists(id);
            if (score < 1 || score > 5) {
                throw new SystemException(GeneralErrorMessage.ILLEGAL_SCORE_ARGUMENT_EXCEPTION);
            }

            Integer oldScore = restaurant.getReviewScore();
            restaurant.setReviewScore(restaurant.getReviewScore() + score);
            setBaseAdditionalFields(restaurant);
            restaurant = restaurantRepository.save(restaurant);
            log.info("Restaurant with id {} review score updated. Old score: {}. New score: {}", id, oldScore, restaurant.getReviewScore());
            return ("Restaurant with id " + id + " review score updated. Old score: " + oldScore + ". New score: " + restaurant.getReviewScore());
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage());
            throw new SystemException(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }


    private void checkUniqueness(String email, String phoneNumber, String id) {
        if (email.isBlank() && restaurantRepository.existsRestaurantByEmailAndIdNot(email, id)) {
            throw new SystemException(GeneralErrorMessage.DUPLICATE_EMAIL);
        }
        if (phoneNumber.isBlank() && restaurantRepository.existsRestaurantByPhoneNumberAndIdNot(phoneNumber, id)) {
            throw new SystemException(GeneralErrorMessage.DUPLICATE_PHONE_NUMBER);
        }
    }

    private Restaurant getRestaurantByIdIfExists(String id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new SystemException(GeneralErrorMessage.ITEM_NOT_FOUND));
    }

    private void setBaseAdditionalFields(Restaurant restaurant) {
        LocalDateTime now = LocalDateTime.now();
        if (restaurant.getId() == null) {
            restaurant.setCreateDate(now);
            restaurant.setReviewScore(0);
        }
        restaurant.setUpdateDate(now);
    }


    private void validateLocation(Double latitude, Double longitude) {
        if (latitude == null || (latitude < -90 || latitude > 90)) {
            throw new SystemException(GeneralErrorMessage.INVALID_LATITUDE);
        }
        if (longitude == null || (longitude < -180 || longitude > 180)) {
            throw new SystemException(GeneralErrorMessage.INVALID_LONGITUDE);
        }
    }
}
