package com.clementang.shoppingcart.model;

import lombok.NoArgsConstructor;

import java.util.Collection;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Table(name = "privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;


    public Privilege(String privilege) {

        this.name = privilege;
    }
   public String getPrivilege() {
       return name;
   }

    public void setPrivilege(String privilege) {
        this.name = privilege;
    }

}
