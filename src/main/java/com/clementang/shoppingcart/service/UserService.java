package com.clementang.shoppingcart.service;


import com.clementang.shoppingcart.model.*;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    User saveUser(User user);

    List<User> findAll();

    void delete(Long id);

    Optional<User> findOne(Long id);




}
