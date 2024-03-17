package com.zehraarslan.restaurantservice.dto;

import org.springframework.data.solr.core.mapping.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record RestaurantDto(String id,
                            String name,
                            String email,
                            String phoneNumber,
                            String description,
                            Double latitude,
                            Double longitude,
                            Integer reviewScore,
                            LocalDateTime createDate,
                            LocalDateTime updateDate) {
}

