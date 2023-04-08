package com.clementang.shoppingcart.security;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.clementang.shoppingcart.model.User;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser implements HttpSessionBindingListener, Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> usernames = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!usernames.contains(user.getUsername())) {
            usernames.add(user.getUsername());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> usernames = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        usernames.remove(user.getUsername());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
