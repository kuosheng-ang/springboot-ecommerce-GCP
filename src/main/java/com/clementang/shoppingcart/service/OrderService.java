package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.*;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress, Payment payment,
			String shippingMethod,
			Customer customer);
	
	Order findById(Long id);
}
