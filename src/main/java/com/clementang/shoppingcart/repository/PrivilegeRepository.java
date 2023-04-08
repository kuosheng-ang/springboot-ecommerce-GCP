package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.Privilege;
import com.clementang.shoppingcart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("SELECT p FROM Privilege p WHERE LOWER(p.name) = LOWER(:privilege)")
    Privilege findByName( String privilege);

    Privilege save(Privilege privilege);
    /*@Query("SELECT r FROM Role r WHERE LOWER(r.role_desc) = LOWER(:role_desc)")
    Role findByName(@Param("role_desc") String roleName);*/

}
