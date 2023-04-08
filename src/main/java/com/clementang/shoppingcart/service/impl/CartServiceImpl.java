package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;

	
	public List<Cart> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartRepository.findByShoppingCart(shoppingCart);
	}
	
	public Cart updateCartItem(Cart cartItem)  {


		BigDecimal bigDecimal = new BigDecimal(cartItem.getCartPrice()).multiply(new BigDecimal(cartItem.getCartQuantity()));
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
		cartItem.setCartTotal(bigDecimal);
		cartRepository.save(cartItem);
		
		return cartItem;
	}
	
	public Cart addProductToCartItem(Product product, User user, int qty)  {


		//List<Cart> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		/*for (Cart cartItem : cartItemList) {
			if(product.getId() == cartItem.getProductId()) {
				cartItem.setCartQuantity(cartItem.getCartQuantity()+qty);
				cartItem.setCartTotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}*/
		
		Cart cart = new Cart();
		//cart.setShoppingCart(user.getShoppingCart());
		//cartItem.setProduct(product);
		cart.setProductId(product.getId());
		
		cart.setCartQuantity(qty);
		cart.setCartTotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
		cart = cartRepository.save(cart);

		return cart;
	}

	/**
	 * If product is in the map just increment quantity by 1.
	 * If product is not in the map with, add it with quantity 1
	 *
	 * @param cartItem
	 */
	/*@Async
	public void addToCart(CartItem cartItem) {
		if (Cart.containsKey(cartItem)) {
			Cart.replace(cartItem, Cart.get(cartItem) + 1);

		} else {
			Cart.put(cartItem, 1);
		}
		cartRepository.saveAndFlush(cartItem);
	}*/

	/**
	 * If product is in the map with quantity > 1, just decrement quantity by 1.
	 * If product is in the map with quantity 1, remove it from map
	 *
	 * @param cartItem
	 */
	//@Override
	/*public void removeProduct(CartItem cartItem) {
		if (Cart.containsKey(cartItem)) {
			if (Cart.get(cartItem) > 1)
				Cart.replace(cartItem, Cart.get(cartItem) - 1);
			else if (Cart.get(cartItem) == 1) {
				Cart.remove(cartItem);
			}
		}
	}*/
	
	public Cart findById(Long id) {
		return cartRepository.findById(id).orElse(null);
	}
	
	public void removeCartItem(Cart cart) {

		cartRepository.delete(cart);
	}
	
	public Cart save(Cart cartItem) {
		return cartRepository.save(cartItem);
	}
	
	/*public List<Cart> findByOrder(Order order) {
		return cartRepository.findByOrder(order);
	}*/

}
