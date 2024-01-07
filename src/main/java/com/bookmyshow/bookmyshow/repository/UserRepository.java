package com.bookmyshow.bookmyshow.repository;

import com.bookmyshow.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //SQL queries

    @Override
    Optional<User> findById(Long aLong);
    Optional<User> findByEmail(String email);
    @Override
    User save(User user);
}
