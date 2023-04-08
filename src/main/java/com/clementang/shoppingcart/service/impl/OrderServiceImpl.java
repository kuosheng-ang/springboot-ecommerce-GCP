package com.clementang.shoppingcart.service.impl;



import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.model.ShippingAddress;
import com.clementang.shoppingcart.repository.OrderRepository;
import com.clementang.shoppingcart.service.CartService;
import com.clementang.shoppingcart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress, Payment payment,
			String shippingMethod,
			Customer customer) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<Cart> cartItemList = cartService.findByShoppingCart(shoppingCart);
		
		for(Cart cartItem : cartItemList) {
			//Product product = cartItem.getProduct();
			//cartItem.setOrder(order);
			//product.setInStockQuantity(product.getInStockQuantity() - cartItem.getCartQuantity());
		}
		
		order.setCartItemList(cartItemList);
		//order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderAmount(shoppingCart.getGrandTotal().doubleValue());
		//shippingAddress.setOrder(order);
		//billingAddress.setOrder(order);
		order.setCustomer(customer);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

}
