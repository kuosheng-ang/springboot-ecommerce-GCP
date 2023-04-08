package com.clementang.shoppingcart.model;

import java.util.*;
import java.util.regex.Pattern;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data  //need in order to have the email working
@Entity
@Table(name = "users")
public class User implements UserDetails  {

    //private static final long serialVersionUID = 1l;

    //private static Pattern EMAIL_PATTERN = Pattern.compile("\"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$\";");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Size(min = 2, message = "Username must be at least 2 characters long")
    @Column(name = "username",  updatable = true)
    public String username;
    @Column(name = "firstname", nullable = false, updatable = true)
    private String firstname;
    @Column(name = "lastname", nullable = false, updatable = true)
    private String lastname;

    @Column(name = "phone_number")
    private String phone;

    @Email(message = "Please enter a valid email")
    @Column(name = "email", nullable = false, updatable = true)
    @NotNull(message = "*Please provide an email")
    private String email;
    //private boolean matches(String email) { return EMAIL_PATTERN.matcher(email).matches();}

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Transient
    private String confirmPassword;

    /*@ManyToMany
    @JoinTable(
            name = "role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "role"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;*/

    //@OneToOne(cascade = {CascadeType.MERGE})
    //@Column(name = "role")  // @Column(s) not allowed on a @OneToOne property
    //private Role role;

    @Column(name = "active")
    public boolean enabled = true;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShoppingCart shoppingCart;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;*/


    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "role_id") //@JoinTable(name = "role", joinColumns = @JoinColumn(name = "id"))  // //@JoinTable(name = "user_role")
    //@OneToMany(cascade = CascadeType.ALL)
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinTable(name = "role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    //@JoinColumn(name = "role_id")
    //@JsonIgnore
    //public Role role;
    //private Set<Role> roles = new HashSet<>();

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@Column(name = "userRole")
    // @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))  //@JoinTable(name = "role")
    //@JsonIgnore
    // private Role userRole;
    public User() {}

    public User(String username) {
        this.username = username;
    }

    public User( String username,  String firstName, String lastName, String encodePassword,  String email,  Collection<Role> roles, String phone, boolean active ) {

        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
        this.password = encodePassword;
        this.email = email;
        this.roles = roles;
        this.phone = phone;
        this.enabled = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
/*
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }*/

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "user_id")
    @JsonIgnore
    public Set<Cart> cart = new HashSet<>();*/

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String emailAddress) {
        this.email = emailAddress;
    }

    /*public void setUserRole(Role role) {
        this.role = role;
    }
    public Role getUserRole() {
        return this.role;
    }*/

}
