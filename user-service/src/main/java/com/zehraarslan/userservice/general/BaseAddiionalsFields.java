package com.zehraarslan.userservice.general;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class BaseAddiionalsFields {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
