package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.Order;
import com.clementang.shoppingcart.model.Product;
import com.clementang.shoppingcart.model.User;
import com.clementang.shoppingcart.model.*;

import java.util.List;

public interface CartService {
	List<Cart> findByShoppingCart(ShoppingCart shoppingCart);
	
	Cart updateCartItem(Cart cartItem);
	
	Cart addProductToCartItem(Product product, User user, int qty) ;
	
	Cart findById(Long id);
	
	void removeCartItem(Cart cartItem);
	
	Cart save(Cart cartItem);
	
	/*List<Cart> findByOrder(Order order);*/
}
