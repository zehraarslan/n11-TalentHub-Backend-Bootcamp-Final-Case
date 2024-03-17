package com.zehraarslan.userservice.dto;

import java.time.LocalDateTime;

public record RestaurantDto(String id,
                            String name,
                            String email,
                            String phoneNumber,
                            String description,
                            Double latitude,
                            Double longitude,
                            Integer reviewScore) {
}
