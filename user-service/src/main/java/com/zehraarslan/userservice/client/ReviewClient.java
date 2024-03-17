package com.zehraarslan.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "review", url = "http://localhost:8081/api/v1/restaurants")
public interface ReviewClient {
        @PutMapping("/{id}/review-score")
        String updateReviewScore(String id, Integer score);
}
