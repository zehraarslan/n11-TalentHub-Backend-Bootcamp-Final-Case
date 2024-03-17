package com.zehraarslan.restaurantservice.request;

public record RestaurantUpdateRequest(String name,
                                      String email,
                                      String phoneNumber,
                                      String description) {
}
