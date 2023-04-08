package com.clementang.shoppingcart.service.impl;

import com.clementang.shoppingcart.model.Customer;
import com.clementang.shoppingcart.model.ShippingAddress;
import com.clementang.shoppingcart.repository.CustomerRepository;
import com.clementang.shoppingcart.repository.RoleRepository;
import com.clementang.shoppingcart.repository.ShippingAddressRepository;
import com.clementang.shoppingcart.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository _customerRepository;

    @Autowired
    private ShippingAddressRepository _shippingAddressRepository;

    /*@Autowired
    private RoleRepository roleRepository;*/

    @Autowired
    private RoleRepository _RoleRepository;

    /*private final PasswordEncoder passwordEncoder;*/

    private static final String USER_ROLE = "ROLE_USER";

    /*@Autowired
    public _userServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this._userRepository = userRepository;
        this._userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }*/

    //@Override
    public Customer findByUsername(String username) {
        return _customerRepository.findCustomerByUserName(username);
    }


    public Customer save(Customer customer) {
        // Encode plaintext password
        /*user.setPassword(passwordEncoder.encode(user.getPassword()));*/

        // Set Role to ROLE_USER
        //user.setUserRole(_RoleRepository.findByRole(USER_ROLE));
        return _customerRepository.saveAndFlush(customer);
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) _customerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        _customerRepository.deleteById(id);
    }

    @Override
    public void setCustomerDefaultShipping(Long shippingId, Customer customer) {
        //List<Customer> customerShippingList = (List<Customer>) _customerRepository.findAll();

        for (ShippingAddress customerShipping : customer.getShippingList()) {
            if(customerShipping.getShippingAddressId() == shippingId) {
                customerShipping.setCustomerShippingDefault(true);
                _shippingAddressRepository.save(customerShipping);
            } else {
                customerShipping.setCustomerShippingDefault(false);
                _shippingAddressRepository.save(customerShipping);
            }
        }
    }

    public void updateCustomerShipping(ShippingAddress shippingAddress, Customer customer){
        shippingAddress.setCustomer(customer);
        shippingAddress.setCustomerShippingDefault(true);
        customer.getShippingList().add(shippingAddress);
        save(customer);
    }


}
