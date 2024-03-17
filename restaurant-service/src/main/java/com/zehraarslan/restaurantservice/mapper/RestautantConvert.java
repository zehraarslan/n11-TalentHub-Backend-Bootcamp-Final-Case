package com.zehraarslan.restaurantservice.mapper;

import com.zehraarslan.restaurantservice.dto.RestaurantDto;
import com.zehraarslan.restaurantservice.entity.Restaurant;
import com.zehraarslan.restaurantservice.request.RestaurantSaveRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestautantConvert {
    public Restaurant convertToRestaurant(RestaurantSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName( request.name() );
        restaurant.setDescription( request.description() );
        restaurant.setEmail( request.email() );
        restaurant.setPhoneNumber( request.phoneNumber() );
        restaurant.setPassword( request.password() );
        restaurant.setLatitude( request.latitude() );
        restaurant.setLongitude( request.longitude() );

        return restaurant;
    }

    public RestaurantDto convertToRestaurantDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;
        String phoneNumber = null;
        String description = null;
        Double latitude = null;
        Double longitude = null;
        Integer reviewScore = null;
        LocalDateTime createDate = null;
        LocalDateTime updateDate = null;

        id = restaurant.getId();
        name = restaurant.getName();
        email = restaurant.getEmail();
        phoneNumber = restaurant.getPhoneNumber();
        description = restaurant.getDescription();
        latitude = restaurant.getLatitude();
        longitude = restaurant.getLongitude();
        reviewScore = restaurant.getReviewScore();
        createDate = restaurant.getCreateDate();
        updateDate = restaurant.getUpdateDate();

        RestaurantDto restaurantDto = new RestaurantDto( id, name, email, phoneNumber, description, latitude, longitude, reviewScore, createDate, updateDate );

        return restaurantDto;
    }


    public List<RestaurantDto> convertToRestaurantDtos(List<Restaurant> restaurants) {
        if ( restaurants == null ) {
            return null;
        }

        List<RestaurantDto> list = new ArrayList<RestaurantDto>( restaurants.size() );
        for ( Restaurant restaurant : restaurants ) {
            list.add( convertToRestaurantDto( restaurant ) );
        }

        return list;
    }
}
