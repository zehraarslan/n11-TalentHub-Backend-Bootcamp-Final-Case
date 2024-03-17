package com.zehraarslan.restaurantservice.mapper;

import com.zehraarslan.restaurantservice.dto.RestaurantDto;
import com.zehraarslan.restaurantservice.entity.Restaurant;
import com.zehraarslan.restaurantservice.request.RestaurantSaveRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant convertToRestaurant(RestaurantSaveRequest request);
    RestaurantDto convertToRestaurantDto(Restaurant restaurant);
    List<RestaurantDto> convertToRestaurantDtos (List<Restaurant> restaurants);
}
