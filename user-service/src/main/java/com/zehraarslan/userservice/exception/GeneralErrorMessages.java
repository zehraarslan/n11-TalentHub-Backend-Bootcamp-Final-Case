package com.zehraarslan.userservice.exception;

import java.time.LocalDateTime;

public record GeneralErrorMessages(LocalDateTime date, String message, String description) {
}
