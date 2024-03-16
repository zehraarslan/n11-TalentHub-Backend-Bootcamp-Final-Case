package com.zehraarslan.userservice.general;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity{
    @Embedded
    private BaseAddiionalsFields baseAddiionalsFields;
}
