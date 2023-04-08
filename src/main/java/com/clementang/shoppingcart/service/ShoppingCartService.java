package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.*;

import java.util.Map;

public interface ShoppingCartService {

    void addToCart(Cart cart);

    void removeProduct(Cart cart);
    Map<Cart, Integer> getProductsInCart();

   /* void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();*/
}
