package com.clementang.shoppingcart.service;

import com.clementang.shoppingcart.model.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {

    void saveShippingAddress(ShippingAddress shippingAddress);
    List<ShippingAddress> getAllShippingAddress();
    ShippingAddress findShippingAddressById(Long id);

     ShippingAddress findShippingAddressByCustomer(String customerUserName);



}
