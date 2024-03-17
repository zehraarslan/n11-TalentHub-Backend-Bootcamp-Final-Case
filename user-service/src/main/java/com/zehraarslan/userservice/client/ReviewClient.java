package com.zehraarslan.userservice.client;

import com.zehraarslan.userservice.dto.RestaurantDto;
import com.zehraarslan.userservice.general.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "review", url = "http://localhost:8081/api/v1/restaurants")
public interface ReviewClient {
        @PutMapping("/{id}/review-score")
        ResponseEntity<RestResponse<String>> updateReviewScore(@PathVariable String id, @RequestParam Integer score);

        @GetMapping
        ResponseEntity<RestResponse<List<RestaurantDto>>> getAllRestaurants();
}
