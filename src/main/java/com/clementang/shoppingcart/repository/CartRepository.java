package com.clementang.shoppingcart.repository;


import com.clementang.shoppingcart.model.Cart;
//import org.springframework.data.repository.CrudRepository;
import com.clementang.shoppingcart.model.Order;
import com.clementang.shoppingcart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT c FROM Cart c WHERE LOWER(c.id) = LOWER(:id)")
	Cart findByCartId(@Param("id") UUID id);

	List<Cart> findByShoppingCart(ShoppingCart shoppingCart);

	List<Cart> findByOrder(Order order);

	void delete(Cart cartItem);

}
