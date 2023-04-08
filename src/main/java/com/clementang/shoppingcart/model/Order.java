package com.clementang.shoppingcart.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreatedDate
    private LocalDate orderDate;

    private Double orderAmount;

    private Date shippingDate;
    private String shippingMethod;

    private String orderStatus;

    @OneToOne(cascade=CascadeType.ALL)
    private Payment payment;

    @OneToOne(cascade=CascadeType.ALL)
    private ShippingAddress shippingAddress;

    @OneToOne(cascade=CascadeType.ALL)
    private BillingAddress billingAddress;

    @OneToMany
    @JoinTable(name = "orders_cartItem",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "cartItem_id", referencedColumnName = "id"))
    private Collection<Cart> cartItemList  = new ArrayList<Cart>();

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    private Customer customer;

    /*@ManyToMany(mappedBy="orders")
    private Collection<Customer> customers = new ArrayList<Customer>();*/

    @ManyToMany(mappedBy="orders")
    private Collection<Product> products = new ArrayList<Product>();

    public Order() {}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomers(Customer customer) {
        this.customer = customer;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Collection<Cart> getCartItemList() {
        return cartItemList;
    }
    public void setCartItemList(Collection<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }


/*    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }*/




}
