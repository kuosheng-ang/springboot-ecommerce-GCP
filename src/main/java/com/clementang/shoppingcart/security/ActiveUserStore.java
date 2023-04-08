package com.clementang.shoppingcart.security;

import com.clementang.shoppingcart.model.User;
import com.clementang.shoppingcart.repository.UserRepository;
import com.clementang.shoppingcart.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ActiveUserStore {


    private UserRepository _userRepo;

    public List<String> users;

    public ActiveUserStore(){
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users = _userRepo.getAllUsername();
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}