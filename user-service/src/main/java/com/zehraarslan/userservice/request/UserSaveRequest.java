package com.zehraarslan.userservice.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public record UserSaveRequest(@NotBlank(message = "Name is required")
                              @Size(max = 100, message = "Name cannot exceed 100 characters")
                              String name,

                              @NotBlank(message = "Surname is required")
                              @Size(max = 100, message = "Surname cannot exceed 100 characters")
                              String surname,

                              @NotBlank(message = "Username is required")
                              @Size(max = 100, message = "Username cannot exceed 100 characters")
                              String userName,

                              @NotBlank(message = "Password is required")
                              @Size(max = 100, message = "Password cannot exceed 100 characters")
                              String password,

                              @NotBlank(message = "Phone number is required")
                              String phoneNumber,

                              @NotBlank(message = "Email is required")
                              String email,

                              @NotNull(message = "Latitude is required")
                              double latitude,

                              @NotNull(message = "Longitude is required")
                              double longitude) {


}
