package com.zehraarslan.restaurantservice.controller;

import com.zehraarslan.restaurantservice.dto.RestaurantDto;
import com.zehraarslan.restaurantservice.entity.Restaurant;
import com.zehraarslan.restaurantservice.general.RestResponse;
import com.zehraarslan.restaurantservice.repository.RestaurantRepository;
import com.zehraarslan.restaurantservice.request.RestaurantSaveRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateLocationRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdatePasswordRequest;
import com.zehraarslan.restaurantservice.request.RestaurantUpdateRequest;
import com.zehraarslan.restaurantservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@Slf4j
 public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<RestaurantDto>>> getAllRestaurants() {
        log.info("Retrieving all restaurants");
        List<RestaurantDto> restaurantDtos = restaurantService.getAll();
        return ResponseEntity.ok(RestResponse.of(restaurantDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantDto>> getRestaurantById(@PathVariable String id) {
        log.info("Retrieving restaurant by id: {}", id);
        RestaurantDto restaurantDto = restaurantService.getById(id);
        return  ResponseEntity.ok(RestResponse.of(restaurantDto));
    }

    @PostMapping
    public ResponseEntity<RestResponse<RestaurantDto>> saveRestaurant(@RequestBody RestaurantSaveRequest request) {
        log.info("Creating restaurant with request: {}", request);
        RestaurantDto restaurantDto = restaurantService.save(request);
        return ResponseEntity.ok(RestResponse.of(restaurantDto));
    }

    @PostMapping("/saveAll")
    public ResponseEntity<RestResponse<List<RestaurantDto>>> saveAllRestaurants(@RequestBody List<RestaurantSaveRequest> requests) {

            List<RestaurantDto> restaurantDtos = restaurantService.saveAll(requests);
            return ResponseEntity.ok(RestResponse.of(restaurantDtos));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantDto>> updateRestaurant(@PathVariable String id, @RequestBody RestaurantUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.update(id, request)));
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<RestResponse<RestaurantDto>> updateRestaurantLocation(@PathVariable String id, @RequestBody RestaurantUpdateLocationRequest request) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.updateCustomerLocation(id, request)));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<RestResponse<RestaurantDto>> updateRestaurantPassword(@PathVariable String id, @RequestBody RestaurantUpdatePasswordRequest request) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.updateCustomerPassword(id, request)));
    }

    @PutMapping("/{id}/review-score")
    public ResponseEntity<RestResponse<String>> updateReviewScore(@PathVariable String id, @RequestParam Integer score) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.updateReviewScore(id, score)));
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
    }

    @DeleteMapping()
    public void deleteAllRestaurant() {
        restaurantService.deleteAllRestaurant();
    }
}
