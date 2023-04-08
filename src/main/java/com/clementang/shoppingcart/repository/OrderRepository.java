package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.BillingAddress;
import com.clementang.shoppingcart.model.Order;
import com.clementang.shoppingcart.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE LOWER(o.id) = LOWER(:id)")
	Order findOrderById(Long id);



}
