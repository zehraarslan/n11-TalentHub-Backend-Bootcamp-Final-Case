package com.zehraarslan.userservice.mapper;

import com.zehraarslan.userservice.dto.RestaurantDto;
import com.zehraarslan.userservice.dto.RestaurantSuggestionsDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantSuggestionsDto convertToRestauran(RestaurantDto restaurantDto, Double recommendationScore);
}
