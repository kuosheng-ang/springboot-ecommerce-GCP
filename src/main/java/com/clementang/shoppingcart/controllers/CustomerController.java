package com.clementang.shoppingcart.controllers;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.service.*;
import com.clementang.shoppingcart.security.*;
import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.util.USStates;
import com.clementang.shoppingcart.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService _customerService;

    @Autowired
    private UserService _userService;

    @Autowired
    private OrderService _orderService;

    @Autowired
    private ShippingAddressService _shippingAddressService;

    @Autowired
    private myUserDetailsService _userSecurityService;


    @RequestMapping("/profile")
    public String index(Model model, Principal principal) {
        boolean cartActive = true;

        Customer customer = customerRepository.findCustomerByUserName(principal.getName());
        User user = _userService.findByUsername(principal.getName());
        String authUserName = _userSecurityService.getCurrentUser().getName();
        BillingAddress customerBillAddress = customer.getBillingAddress();
        ShippingAddress customerShipAddress = customer.getShippingAddress();
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("customerUserName", customer.getCustomerUserName());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("customerBillingAddressCity", customerBillAddress.getCity());
        model.addAttribute("customerBillingAddressState", customerBillAddress.getState());
        model.addAttribute("customerBillingAddressCountry", customerBillAddress.getCountry());
        model.addAttribute("customerBillingAddressZipCode", customerBillAddress.getZipCode());
        model.addAttribute("customerShippingAddressCountry", customerShipAddress.getCountry());
        model.addAttribute("classActiveEdit", true);
        model.addAttribute("authUserName", authUserName);
        List<USStates> stateList = Arrays.asList(USStates.values());
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);

        return "customer/profile/index";
    }

    @RequestMapping(value ="/updateCustomerInfo", method = RequestMethod.POST)
    public String updateCustomerInfo(
          /*  @ModelAttribute("customer") Customer customer,
              BindingResult bindingResult,*/
            Principal principal,
            @ModelAttribute("user") User user,
            @ModelAttribute("newPassword") String newPassword,
            BindingResult bindingResult,
            Model model) throws Exception {

        User currentUser = _userService.findByUsername(principal.getName());
        //User currentUser = _userService.findByUsername(user.getUsername());
        if(currentUser == null) {
            throw new Exception ("Customer not found");
        }

        /*check email already exists*/
        /*if (_userService.findByEmail(user.getEmail())!=null) {
            if(_userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
                model.addAttribute("emailExists", true);
                return "myProfile";
            }
        }*/

        /**
         * check whether customer & username already exists
         * */

        if (_userService.findByUsername(user.getUsername())!=null) {

            model.addAttribute("usernameExists", true);

            //update password
            if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {

                BCryptPasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();
                String dbPassword = currentUser.getPassword();
                if(passwordEncoder.matches(user.getPassword(), dbPassword)){
                    currentUser.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    model.addAttribute("incorrectPassword", true);
                    return "customer/profile/index";
                }
            }
        }
        else if (bindingResult.hasErrors())  {
            System.out.println("Binding result error");
            return "customer/profile/index";
        }

        /* model.addAttribute("updateSuccess", true);
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("classActiveEdit", true);

        UserDetails userDetails = _userSecurityService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("orderList", customer.getOrders());*/
        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
        currentUser.setEmail(user.getEmail());
        _userService.saveUser(currentUser);
        return "customer/profile/index";

    }

    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {

        Customer customer = _customerService.findByUsername(principal.getName());
        User user = _userService.findByUsername(principal.getName());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("customerShippingList", customer.getShippingList());
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);

        return "customer/profile/index";
    }

    @RequestMapping(value="/setDefaultShippingAddress", method=RequestMethod.POST)
    public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long defaultShippingId,
                                            Principal principal, Model model ) {


        Customer customer = _customerService.findByUsername(principal.getName());
        User user = _userService.findByUsername(principal.getName());
        ShippingAddress customerSA = _shippingAddressService.findShippingAddressByCustomer(principal.getName());
        _customerService.setCustomerDefaultShipping(defaultShippingId, customer);

        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("zipcode", customerSA.getZipCode());
        model.addAttribute("streetName", customerSA.getStreetName());
        model.addAttribute("customerShippingList", customer.getShippingList());
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "customer/profile/profile";
    }

    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model, Principal principal){

        User user = _userService.findByUsername(principal.getName());
        Customer customer = _customerService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);

        ShippingAddress userShipping = new ShippingAddress();
        model.addAttribute("userShipping", userShipping);
        model.addAttribute("userShippingList", customer.getShippingList());

        List<USStates> stateList = Arrays.asList(USStates.values());
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);
        return "customer/profile/index";
    }

    @RequestMapping(value="/addNewShippingAddress", method=RequestMethod.POST)
    public String addNewShippingAddress( @ModelAttribute("userShipping") ShippingAddress userShipping,
                                         Principal principal, Model model){

        User user = _userService.findByUsername(principal.getName());
        Customer customer = _customerService.findByUsername(principal.getName());
        _customerService.updateCustomerShipping(userShipping, customer);

        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("updateUserShippingInfo", true);

        return "customer/profile/index";
    }

   /* @RequestMapping("/orderDetail")
    public String orderDetail(@RequestParam("id") Long orderId,
                              Principal principal, Model model) {

        Customer customer = _customerService.findByUsername(principal.getName());
        Order order = _orderService.findById(orderId);

        if(order.getCustomer().getCustomerId() != customer.getCustomerId()) {
            return "badRequestPage";
        } else {
            //List<Cart> cartItemList = _cartService.findByOrder(order);
            //model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("customer", customer);
            model.addAttribute("order", order);

            model.addAttribute("customerOrderList", customer.getOrders());

            return "customer/profile/index";
        }
    }*/
}
