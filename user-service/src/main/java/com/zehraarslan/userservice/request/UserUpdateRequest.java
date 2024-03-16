package com.zehraarslan.userservice.request;

public record UserUpdateRequest(String name,
                                String surname,
                                String userName,
                                String phoneNumber,
                                String email) {
}
