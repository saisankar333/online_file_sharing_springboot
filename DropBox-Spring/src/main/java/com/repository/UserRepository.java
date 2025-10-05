package com.repository;

import com.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmailAndPassword(String email, String password);

    // Change this:
    // List<User> findById(Integer id);
    Optional<User> findById(Integer id); // Use Optional

    User findByEmail(String email);
}