package com.clementang.shoppingcart.security;

import com.clementang.shoppingcart.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class customisedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    ActiveUserStore activeUserStore;

    @Autowired
    private Environment env;

    public customisedAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {

        handle(request, response, authentication);
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(30 * 60);

            String username;
            if (authentication.getPrincipal() instanceof User) {
                username = ((User)authentication.getPrincipal()).getEmail();
            }
            else {
                username = authentication.getName();
            }
            //LoggedUser user = new LoggedUser(username, activeUserStore);
            session.setAttribute("user", username);
        }
        clearAuthenticationAttributes(request);
    }

    protected void handle( HttpServletRequest request,
                           HttpServletResponse response,
                           Authentication authentication ) throws IOException {

        //addWelcomeCookie(getUserName(authentication), response);
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        boolean isUser = false;
        boolean isAdmin = false;
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/customer/profile");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin/products");  //chris.wang, nancy.liu

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
            if (grantedAuthority.getAuthority().equals("READ_PRIVILEGE")) {
                isUser = true;
            } else if (grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")) {
                isAdmin = true;
                isUser = false;
                break;
            }
        }
        if (isUser) {
            String username;
            if (authentication.getPrincipal() instanceof User) {
                username = ((User)authentication.getPrincipal()).getEmail();
            }
            else {
                username = authentication.getName();
            }

            //return "/cart?user="+username;
            return "/customer/profile";
        } else if (isAdmin) {
            return "/admin/categories";
        } else {
            throw new IllegalStateException();
        }

    }
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private String getUserName(final Authentication authentication) {
        return ((User) authentication.getPrincipal()).getUsername();
    }

    private void addWelcomeCookie(final String user, final HttpServletResponse response) {
        //Cookie welcomeCookie = getWelcomeCookie(user);
        //response.addCookie(welcomeCookie);
    }

    private Cookie getWelcomeCookie(final String user) {
        Cookie welcomeCookie = new Cookie("welcome", user);
        welcomeCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        return welcomeCookie;
    }

}
