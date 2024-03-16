package com.zehraarslan.userservice.repository;

import com.zehraarslan.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUserNameAndIdNot(String username, Long id);
    boolean existsUserByEmailAndIdNot(String email, Long id);
    boolean existsUserByPhoneNumberAndIdNot(String phoneNumber, Long id);
}

