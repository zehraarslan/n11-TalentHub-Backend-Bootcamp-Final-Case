package com.zehraarslan.userservice.dto;

public record UserDto(Long id,
                      String name,
                      String surname,
                      String userName,
                      String phoneNumber,
                      String email,
                      double latitude,
                      double longitude) {
}
