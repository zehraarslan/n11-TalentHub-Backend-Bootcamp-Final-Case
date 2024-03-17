package com.zehraarslan.restaurantservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SolrDocument(solrCoreName = "restaurant")
public class Restaurant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "name", type = "string")
    private String name;

    @Indexed(name = "description", type = "string")
    private String description;

    @Indexed(name = "email", type = "string")
    private String email;

    @Indexed(name = "phone_number", type = "string")
    private String phoneNumber;

    @Indexed(name = "restaurant_pass", type = "string")
    private String password;

    @Indexed(name = "latitude", type = "pdouble")
    private Double latitude;

    @Indexed(name = "longitude", type = "pdouble")
    private Double longitude;

    @Indexed(name = "review_score", type = "pint")
    private Integer reviewScore;

    @Indexed(name = "create_date", type = "tdatetime")
    private LocalDateTime createDate;

    @Indexed(name = "update_date", type = "tdatetime")
    private LocalDateTime updateDate;


}
