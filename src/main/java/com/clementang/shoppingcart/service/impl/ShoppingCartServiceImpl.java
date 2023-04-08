package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Dusan
 */
@Service
/*@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)*/
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    private Map<Cart, Integer> Cart = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param cartItem
     */
    @Async
    public void addToCart(Cart cartItem) {
        if (Cart.containsKey(cartItem)) {
            Cart.replace(cartItem, Cart.get(cartItem) + 1);

        } else {
            Cart.put(cartItem, 1);
        }
        cartRepository.saveAndFlush(cartItem);
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param cartItem
     */
    //@Override
    public void removeProduct(Cart cartItem) {
        if (Cart.containsKey(cartItem)) {
            if (Cart.get(cartItem) > 1)
                Cart.replace(cartItem, Cart.get(cartItem) - 1);
            else if (Cart.get(cartItem) == 1) {
                Cart.remove(cartItem);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Cart, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(Cart);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */

    /*public void checkout() throws NotEnoughProductsInStockException {
        Product product;
        for (Map.Entry<Product, Integer> entry : Cart.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.getOne(entry.getKey().getId());
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
        }
        productRepository.save(Cart.keySet());
        Cart.keySet();
        cartRepository.flush();
        Cart.clear();
    }

    @Override
    public Double getTotal() {
        return Cart.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(Double.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }*/
}
