package com.zehraarslan.restaurantservice.service;

import com.zehraarslan.restaurantservice.dto.RestaurantDto;
import com.zehraarslan.restaurantservice.request.RestaurantSaveRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateLocationRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdatePasswordRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getAll();
    RestaurantDto getById(String id);
    RestaurantDto save(RestaurantSaveRequest request);

    RestaurantDto update(String id, RestaurantUpdateRequest request);
    RestaurantDto updateCustomerLocation(String id, RestaurantUpdateLocationRequest request);

    RestaurantDto updateCustomerPassword(String id, RestaurantUpdatePasswordRequest request);
    void deleteRestaurant(String id);

    String updateReviewScore(String id, Integer score);
}
