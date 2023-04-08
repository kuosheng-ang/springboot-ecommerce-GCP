package com.clementang.shoppingcart.service;


import com.clementang.shoppingcart.model.Customer;
import com.clementang.shoppingcart.model.ShippingAddress;

import java.util.List;

public interface CustomerService {
    Customer findByUsername(String username);


    Customer save(Customer customer);

    List<Customer> findAll();

    void delete(Long id);

    void setCustomerDefaultShipping(Long shippingId, Customer customer);

    void updateCustomerShipping(ShippingAddress userShipping, Customer user);
}
