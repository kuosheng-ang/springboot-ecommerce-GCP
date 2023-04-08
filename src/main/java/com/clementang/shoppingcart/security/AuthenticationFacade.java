package com.clementang.shoppingcart.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.clementang.shoppingcart.security.impl.*;

public class AuthenticationFacade implements IAuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
