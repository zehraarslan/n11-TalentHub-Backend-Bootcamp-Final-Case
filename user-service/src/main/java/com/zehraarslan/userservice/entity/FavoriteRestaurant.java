package com.zehraarslan.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "FAVORITE_RESTAURANT")
public class FavoriteRestaurant {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FavoriteRestaurant")
    @SequenceGenerator(name = "FavoriteRestaurant", sequenceName = "FAVORITE_RESTAURANT_ID_SEQ")
    @Id
    private Long id;
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
    @Column(name = "RESTAURANT_ID", nullable = false)
    private Long restaurantId;
}
