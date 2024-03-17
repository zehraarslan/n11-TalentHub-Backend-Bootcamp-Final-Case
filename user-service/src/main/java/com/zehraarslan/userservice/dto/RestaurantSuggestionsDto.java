package com.zehraarslan.userservice.dto;

public record RestaurantSuggestionsDto(String id,
                                       String name,
                                       String email,
                                       String phoneNumber,
                                       String description,
                                       Double latitude,
                                       Double longitude,
                                       Integer reviewScore,
                                       Double recommendationScore) {
}
