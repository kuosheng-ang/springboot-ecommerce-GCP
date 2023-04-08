package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.Product;
import com.clementang.shoppingcart.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface RoleRepository extends Repository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE LOWER(r.role_desc) = LOWER(:role)")
    Role findByRole(String role);


    Role save(Role role);
    //Role findByName(String role);

    /*@Query("SELECT r FROM Role r WHERE LOWER(r.role_desc) = LOWER(:role_desc)")
    Role findByName(@Param("role_desc") String roleName);*/

}
