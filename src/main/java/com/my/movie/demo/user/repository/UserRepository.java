package com.my.movie.demo.user.repository;

import com.my.movie.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
}
