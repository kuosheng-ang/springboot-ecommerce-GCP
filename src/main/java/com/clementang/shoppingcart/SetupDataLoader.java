package com.clementang.shoppingcart;

import com.clementang.shoppingcart.model.*;
import com.clementang.shoppingcart.model.Role;
import com.clementang.shoppingcart.model.User;
import com.clementang.shoppingcart.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        /**
         *   create initial privileges
         */

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege, passwordPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege, passwordPrivilege);


        /**
         *  create initial roles
         */

        createRoleIfNotFound("ADMIN", adminPrivileges);
        createRoleIfNotFound("USER", userPrivileges);

        /**
         *    create initial user - assigned with admin role and user role
         */


        Role adminRole = roleRepository.findByRole("ADMIN");

        /*user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("test$123"));
        user.setEmail("admin@emart.com.cn");
        user.setPhone("6532 1698");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);*/
        createUserIfNotFound( "admin", "Test", "Test", "test$123", "admin@emart.com.cn" ,"6532 1698", Arrays.asList(adminRole));

        Role userRole = roleRepository.findByRole("USER");
        createUserIfNotFound( "chris.wang", "chris", "wang", "p97312601z", "chris.wang@baidu.com.cn" ,"6577 3256", Arrays.asList(userRole));
        createUserIfNotFound("janet.lee", "janet", "lee", "p9731260z", "janet.lee@yahoo.com.tw" ,"", Arrays.asList(userRole));


        //createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        /*Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
        User seedDataUser = new User();
        seedDataUser.setUsername("admin1");
        seedDataUser.setFirstname("admin1");
        seedDataUser.setLastname("admin1");
        seedDataUser.setPhone("6513 5960");
        seedDataUser.setPassword(passwordEncoder.encode("p9731260x"));
        seedDataUser.setEmail("admin@emart.com.vn");
        seedDataUser.setRoles(Arrays.asList(adminRole));
        seedDataUser.setEnabled(true);
        userRepository.save(seedDataUser);*/

        /**
         *    create categories
         */

        createCategoryIfNotFound("Comedy", 100, "comedy");
        createCategoryIfNotFound("Crime", 100, "crime");
        createCategoryIfNotFound("Drama", 100, "drama");
        createCategoryIfNotFound("Fantasy", 100, "fantasy");
        createCategoryIfNotFound("Horror", 100, "horror");
        createCategoryIfNotFound("Romance", 100, "romance");
        createCategoryIfNotFound("Thriller", 100, "thriller");

        /**
         *    create products
         */

        createProductIfNotFound("65122587",  "Spy (2015 film)","Spy is a 2015 American spy action comedy film written and directed by Paul Feig.", "Spy (2015 film)", 3, 15.99,  "Spy2015.jpg","Thriller");
        createProductIfNotFound("63195586",  "Avatar: The Way of Water","Avatar: The Way of Water is a 2022 American epic science fiction film directed and produced by James Cameron", "Avatar" ,4, 20.99,  "Avartar.jpg", "Fantasy");
        createProductIfNotFound("63231548",  "The Invitation_(2022_film)","The Invitation is a 2022 American horror thriller film directed by Jessica M. Thompson and written by Blair Butler. The film stars Nathalie Emmanuel ", "horror invitation", 8, 17.99,  "The_Invitation_(2022_film).jpg", "Horror");
        createProductIfNotFound("67224565",  "Gone with the Wind","Gone with the Wind is a 1939 American epic historical romance film adapted from the 1936 novel by Margaret Mitchell. The film was produced by David", "Gone wind", 10, 16.99,  "Gone_With_the_Wind_01.jpg", "Crime");
        createProductIfNotFound("61234579",  "Schindler's List","Schindler's List is a 1993 American epic historical drama film directed and produced by Steven Spielberg and written by Steven Zaillian", "Schindler",15, 18.99,  "Schindler's List.jpg", "Fantasy");
        createProductIfNotFound("68239536",  "Forrest Gump","Forrest Gump is a 1994 American comedy-drama film directed by Robert Zemeckis and written by Eric Roth. It is based on the 1986 novel of the same name", "Forest Gump",12, 15.99,  "Forrest Gump.jpg", "Horror");
        createProductIfNotFound("63780519",  "The Matrix","The Matrix is a 1999 science fiction action film written and directed by the Wachowskis. ... When computer programmer Thomas Anderson, under the hacker alias", "Matrix",8, 12.99,  "The Matrix.jpg", "Crime");
        createProductIfNotFound("61234709",  "Saving Private Ryan","Set in 1944 during the Normandy landings of World War II, it follows a group of soldiers, led by Captain John Miller (Tom Hanks), on their mission to extricate", "war saving private", 5, 14.99,  "Saving Private Ryan.jpg", "Drama");
        createProductIfNotFound("62879513",  "Terminator 2: Judgment Day", "Terminator 2: Judgment Day is a 1991 American science-fiction action film directed by James Cameron, who co-wrote the script with William Wisher","Terminator", 5, 17.99,  "Terminator 2-Judgment Day.jpg", "Comedy");
        createProductIfNotFound("62879513",  "Goodfellas","Goodfellas (stylized GoodFellas) is a 1990 American biographical crime film directed by Martin Scorsese, written by Nicholas Pileggi and Scorsese", "Goodfellas",7, 19.99,  "Goodfellas.jpg", "Thriller");


        /**
         *    create billing address
         */

        BillingAddress chrisWangBillingAddress = createBillingAddressIfNotFound("chris.wang", "235 Elmwood Ave, Elmira, NY 14903", "Elmira", "NY", "USA", "14903");


        /**
         *    create shipping address
         */

        ShippingAddress chrisWangShippingAddress = createShippingAddressIfNotFound("chris.wang", "235 Elmwood Ave, Elmira, NY 14903", "Level 10 unit 52", "Elmira","NY", "USA", "14903");


        /**
         *    create customer
         */

        Customer chrisWangCustomer =  createCustomerIfNotFound("chris.wang", chrisWangBillingAddress, chrisWangShippingAddress);

        /**
         *    update billingAddress
         */

        updateBillingAddressIfFound(chrisWangCustomer);


        /**
         *    update shippingAddress
         */

        updateShippingAddressIfFound(chrisWangCustomer);

        /**
         *    create order
         */

       //createOrderIfNotFound(chrisWangCustomer,  58.95);


        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByRole(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    Category createCategoryIfNotFound(final String categoryName, final int sortingType, final String slug) {

        Category category = categoryRepository.findByName(categoryName);
        if (category  == null) {
            category = new Category();
            category.setCategoryName(categoryName);
            category.setSlug(slug);
            category.setSorting(sortingType);

        }

        category = categoryRepository.save(category);
        return category;
    }

    @Transactional
    User createUserIfNotFound(final String username, final String firstName, final String lastName, final String password, final String email, final String contactNumber, final Collection<Role> roles) {
        User initialUser = userRepository.findByUsername(username);
        if (initialUser == null) {
            initialUser = new User(username, firstName, lastName, passwordEncoder.encode(password), email, roles, contactNumber,true);
            /*user.setId(userid);
            user.setUsername(username);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);*/
        }
        //user.setRoles(roles);
        initialUser = userRepository.save(initialUser);
        return initialUser;
    }

    @Transactional
    Product createProductIfNotFound(final String productSearch, final String title, final String description, final String slug,  final int availStockQty, final Double retailPrice,  final String image, final String categoryGenre) {

        Clock cl = Clock.systemDefaultZone();
        //Product productObj = productRepository.findProductByDescriptionAndInStockNumber(productSearch, productSearch);
        Product productObj = productRepository.findProductByInStockNumber(productSearch);
        Category categoryObj = categoryRepository.findByName(categoryGenre);
        if (productObj  == null) {
            productObj = new Product();
            productObj.setInStockNumber(productSearch);
            productObj.setSlug(slug);
            productObj.setPrice(retailPrice);
            productObj.setProductImage(image);
            productObj.setNamedTitle(title);
            productObj.setDescription(description);
            productObj.setCategoryId(categoryObj.getId());
            productObj.setInStockQuantity(availStockQty);
            productObj.setCreationDate(LocalDateTime.now(cl));

        }

        productObj = productRepository.save(productObj);
        return productObj;
    }

    @Transactional
    BillingAddress createBillingAddressIfNotFound(final String username, final String streetName, final String city, final String state, final String country, final String zipCode) {
        BillingAddress initialBillingAddress = billingAddressRepository.findBillingAddressByCustomerUserName(username);
        if (initialBillingAddress  == null) {

            //initialBillingAddress  = new BillingAddress(username, streetName, city, state, country, zipCode);

            initialBillingAddress  = new BillingAddress();
            initialBillingAddress.setStreetName(streetName);
            initialBillingAddress.setCustomerUserName(username);
            initialBillingAddress.setCustomer(null);
            initialBillingAddress.setCity(city);
            initialBillingAddress.setState(state);
            initialBillingAddress.setCountry(country);
            initialBillingAddress.setZipCode(zipCode);


        }
        initialBillingAddress = billingAddressRepository.save(initialBillingAddress);
        return initialBillingAddress;
    }

    ShippingAddress createShippingAddressIfNotFound(final String username, final String streetName,  final String apartmentNumber,  final String city, final String state, final String country, final String zipCode) {
        ShippingAddress initialShippingAddress = shippingAddressRepository.findShippingAddressByCustomerUserName(username);
        if (initialShippingAddress  == null) {

            //initialShippingAddress  = new ShippingAddress(username, streetName, apartmentNumber, city, state, country, zipCode);
            initialShippingAddress  = new ShippingAddress();
            initialShippingAddress.setCustomerUserName(username);
            initialShippingAddress.setStreetName(streetName);
            initialShippingAddress.setApartmentNumber(apartmentNumber);
            initialShippingAddress.setCustomer(null);
            initialShippingAddress.setCity(city);
            initialShippingAddress.setState(state);
            initialShippingAddress.setCountry(country);
            initialShippingAddress.setZipCode(zipCode);

        }
        initialShippingAddress = shippingAddressRepository.save(initialShippingAddress);
        return initialShippingAddress;
    }

    @Transactional
    Customer createCustomerIfNotFound(final String username, final BillingAddress billingAddress, final ShippingAddress shippingAddress) {
        Customer initialCustomer = customerRepository.findCustomerByUserName(username);
        if (initialCustomer  == null) {

            //initialCustomer  = new Customer(username, billingAddress, shippingAddress, order);
            initialCustomer  = new Customer();
            initialCustomer.setCustomerUserName(username);
            initialCustomer.setBillingAddress(billingAddress);
            initialCustomer.setShippingAddress(shippingAddress);

        }
        initialCustomer = customerRepository.save(initialCustomer);
        return initialCustomer;
    }

    @Transactional
    BillingAddress updateBillingAddressIfFound(final Customer customer) {
        BillingAddress chrisWangBillingAddressUpdate = billingAddressRepository.findBillingAddressByCustomerUserName("chris.wang");
        if (chrisWangBillingAddressUpdate != null) {

            chrisWangBillingAddressUpdate.setCustomer(customer);
        }
        chrisWangBillingAddressUpdate = billingAddressRepository.save(chrisWangBillingAddressUpdate);
        return chrisWangBillingAddressUpdate;
    }

    @Transactional
    ShippingAddress updateShippingAddressIfFound(final Customer customer) {
        ShippingAddress chrisWangShippingAddressUpdate = shippingAddressRepository.findShippingAddressByCustomerUserName("chris.wang");
        if (chrisWangShippingAddressUpdate != null) {

            chrisWangShippingAddressUpdate.setCustomer(customer);
        }
        chrisWangShippingAddressUpdate = shippingAddressRepository.save(chrisWangShippingAddressUpdate);
        return chrisWangShippingAddressUpdate;
    }

    Order createOrderIfNotFound( final Customer customer, final Double orderAmount) {
        Order initialOrder = orderRepository.findOrderById(Long.parseLong("131"));
        if (initialOrder  == null) {

            initialOrder = new Order();
           // initialOrder = new Order(customer, billingAddress, shippingAddress, orderAmount, today);

          //  initialOrder.setCustomer(customer);
          //  initialOrder.setBillingAddress(billingAddress);
          //  initialOrder.setShippingAddress(shippingAddress);
            initialOrder.setOrderAmount(orderAmount);


        }
        initialOrder = orderRepository.save(initialOrder);
        return initialOrder;
    }




}
