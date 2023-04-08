package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

	@Query("SELECT s FROM ShippingAddress s WHERE LOWER(s.id) = LOWER(:id)")
	ShippingAddress findShippingAddressById(Long id);

	@Query("SELECT s FROM ShippingAddress s WHERE LOWER(s.customerUserName) = LOWER(:username)")
	ShippingAddress findShippingAddressByCustomerUserName(String username);

	@Query("SELECT s FROM ShippingAddress s WHERE LOWER(s.customer.customerId) = LOWER(:customerId)")
	ShippingAddress findShippingAddressByCustomerId(Long customerId);

}
