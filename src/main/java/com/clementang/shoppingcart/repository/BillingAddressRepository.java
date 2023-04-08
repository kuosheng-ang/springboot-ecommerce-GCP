package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {

	@Query("SELECT b FROM BillingAddress b WHERE LOWER(b.id) = LOWER(:id)")
	BillingAddress findBillingAddressById(UUID id);

	@Query("SELECT b FROM BillingAddress b WHERE LOWER(b.customerUserName) = LOWER(:username)")
	BillingAddress findBillingAddressByCustomerUserName(String username);

}
