package com.zehraarslan.restaurantservice.request;

public record RestaurantUpdatePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
}
