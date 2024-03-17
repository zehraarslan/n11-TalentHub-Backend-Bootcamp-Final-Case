package com.zehraarslan.restaurantservice.repository;

import com.zehraarslan.restaurantservice.entity.Restaurant;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {
    List<Restaurant> findAll();
    boolean existsRestaurantByEmailAndIdNot(String email, String id);
    boolean existsRestaurantByPhoneNumberAndIdNot(String phoneNumber, String id);
}
