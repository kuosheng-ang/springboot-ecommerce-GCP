package com.clementang.shoppingcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
//@Service
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig  {

    @Autowired
    //private UserDetailsService userDetailsService;
    private myUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext applicationContext;

    /*private final AccessDeniedHandler accessDeniedHandler;

    final DataSource dataSource;

    @Value("${spring.admin.username}")
    private String adminUsername;

    @Value("${spring.admin.username}")
    private String adminPassword;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public SecurityConfig(AccessDeniedHandler accessDeniedHandler, DataSource dataSource) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Database authentication
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
              //  .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
            // In memory authentication
            auth.inMemoryAuthentication()
                    .withUser(adminUsername).password(adminPassword).roles("ADMIN");
    }*/

/*
     @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("nancy.liu")
                .password("p9731260i")
                .authorities("ROLE_USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("p9731260i")
                .authorities("ROLE_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("p9731260i")
                .authorities("ROLE_ADMIN");

    }*/

    @PostConstruct
    public void completeSetup() {
        userDetailsService = applicationContext.getBean(myUserDetailsService.class);
    }

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /** hasRole('ROLE_ADMIN') as well as hasAuthority('admin') work. **/

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {*/
    @Bean   // Found WebSecurityConfigurerAdapter as well as SecurityFilterChain. Please select just one.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                //.csrf().disable().authorizeRequests()
                .httpBasic()
                .and()
                .authorizeRequests()
            /*.antMatchers("/categories/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .antMatchers("/products/**").hasAnyRole("USER", "ADMIN")*/
            //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            //.antMatchers("/admin/products/**").access("hasRole('ROLE_ADMIN')")
                // .antMatchers("/admin/pages/**").access("hasRole('ROLE_ADMIN')")
                /** hasRole('ROLE_ADMIN') as well as hasAuthority('admin') work. **/

                .antMatchers("/admin/products/*").access("hasRole('ADMIN')")
                .antMatchers("/admin/pages/**").hasRole("ADMIN")
                .antMatchers("/categories/**").permitAll()
                .antMatchers("/admin/categories/**").permitAll()
                .antMatchers("/cart/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/cart").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/page/**").permitAll()
                .antMatchers("/products/**").permitAll()
                .antMatchers("/customer/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .loginProcessingUrl("/login")
               // .defaultSuccessUrl("/cart.html", true)
                .successHandler(myAuthenticationSuccessHandler())
                .and()
             //   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
             //   .and()
                .logout().permitAll()
                .logoutSuccessUrl("/").deleteCookies("remember-me").clearAuthentication(true)
                .and()
                .exceptionHandling().accessDeniedPage("/register")
               /* .and()
                .exceptionHandling()
                .accessDeniedPage("/admin/**");*/
        // http.authorizeRequests()
        //     .antMatchers("/category/**").access("hasRole('ROLE_USER')")
        //     .antMatchers("/").access("permitAll");
                 .and()
                 .csrf()
                .disable();
                 return http.build();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new customisedAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}
