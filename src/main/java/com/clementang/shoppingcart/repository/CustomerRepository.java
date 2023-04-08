package com.clementang.shoppingcart.repository;

import com.clementang.shoppingcart.model.Customer;
import com.clementang.shoppingcart.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerId) = LOWER(:id)")
    Customer findCustomerById(UUID id);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerUserName) = LOWER(:username)")
    Customer findCustomerByUserName(String username);



}
