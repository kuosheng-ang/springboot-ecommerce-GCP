package com.clementang.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clementang.shoppingcart.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    /*User findByEmail(@Param("email") String email);*/
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(:email)")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(:username)")
    User findByUsername(@Param("username") String username);

    @Query("SELECT u.username FROM User u")
    List<String> getAllUsername();

}
