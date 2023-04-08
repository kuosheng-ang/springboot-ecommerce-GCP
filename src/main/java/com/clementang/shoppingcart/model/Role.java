package com.clementang.shoppingcart.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_desc",unique = true, nullable = false)
    private String role_desc;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;


    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role(String roleName) {

        this.role_desc = roleName;
    }

    public String getUserRole() {
        return role_desc;
    }

    public void setUserRole(String userRole) {
        this.role_desc = userRole;
    }

   /* public Collection<Privilege> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User user;*/

}
