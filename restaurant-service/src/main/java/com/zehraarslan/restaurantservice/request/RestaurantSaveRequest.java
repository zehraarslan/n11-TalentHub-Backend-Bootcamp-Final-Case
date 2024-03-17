package com.zehraarslan.restaurantservice.request;


import javax.validation.constraints.NotNull;

public record RestaurantSaveRequest(@NotNull String name,
                                    @NotNull String email,
                                    @NotNull String phoneNumber,
                                    @NotNull String password,
                                    @NotNull String description,
                                    @NotNull Double latitude,
                                    @NotNull Double longitude) {
}


