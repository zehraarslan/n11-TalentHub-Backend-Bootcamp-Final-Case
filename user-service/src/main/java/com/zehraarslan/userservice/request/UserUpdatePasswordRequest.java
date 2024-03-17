package com.zehraarslan.userservice.request;

public record UserUpdatePasswordRequest(String currentPassword,String newPassword, String confirmNewPassword) {
}
