package com.zehraarslan.userservice.entity;


import com.zehraarslan.userservice.general.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User")
    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
    @Id
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Column(name = "SURNAME", length = 100, nullable = false)
    private String surname;

    @NotBlank(message = "Username cannot be blank")
    @Column(name = "USERNAME", length = 100, nullable = false)
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Column(name = "PHONE_NUMBER", length = 100, nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @NotNull(message = "Latitude is required")
    @Column(name = "LATITUDE", nullable = false)
    private double latitude;

    @NotNull(message = "Longitude is required")
    @Column(name = "LONGITUDE", nullable = false)
    private double longitude;
}
