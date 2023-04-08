package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.ShippingAddress;
import com.clementang.shoppingcart.repository.ShippingAddressRepository;
import com.clementang.shoppingcart.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    public void saveShippingAddress(ShippingAddress shippingAddress) {
        shippingAddressRepository.save(shippingAddress);
    }

    public List<ShippingAddress> getAllShippingAddress() {
        return shippingAddressRepository.findAll();
    }
    public ShippingAddress findShippingAddressById(Long id) { return shippingAddressRepository.findShippingAddressById(id); }

    public ShippingAddress findShippingAddressByCustomer(String customerUserName) { return shippingAddressRepository.findShippingAddressByCustomerUserName(customerUserName); }




}