package com.clementang.shoppingcart.service.impl;


import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;/**/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository _userRepository;

    /*@Autowired
    private RoleRepository roleRepository;*/

    @Autowired
    private RoleRepository _RoleRepository;

    /*private final PasswordEncoder passwordEncoder;*/

    private static final String USER_ROLE = "ROLE_USER";

    /*@Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this._userRepository = userRepository;
        this._userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }*/

    //@Override
    public User findByUsername(String username) {
        return _userRepository.findByUsername(username);
    }



    public Optional<User> findByEmail(String email) {
        return _userRepository.findByEmail(email);
    }


    public User saveUser(User user) {
        // Encode plaintext password
        /*user.setPassword(passwordEncoder.encode(user.getPassword()));*/
        user.setEnabled(true);
        // Set Role to ROLE_USER
        //user.setUserRole(_RoleRepository.findByRole(USER_ROLE));
        return _userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) _userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        _userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return _userRepository.findById(id);
    }




}
