package com.zehraarslan.userservice.request;

import jakarta.validation.constraints.NotNull;

public record UserUpdateLocationRequest(@NotNull(message = "Latitude is required")
                                        double latitude,
                                        @NotNull(message = "Longitude is required")
                                        double longitude) {
}
