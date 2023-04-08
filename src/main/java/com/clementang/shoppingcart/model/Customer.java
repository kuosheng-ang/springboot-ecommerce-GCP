package com.clementang.shoppingcart.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import com.clementang.shoppingcart.model.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;

    @JoinColumn(name = "username")
    private String customerUserName;

    @OneToOne
    private BillingAddress billingAddress;

    @OneToOne
    private ShippingAddress shippingAddress;

    @ManyToMany
    @JoinTable(name = "customers_orders",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customerId"))
    private Collection<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<ShippingAddress> ShippingList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Payment> PaymentList;

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }


    public Customer(String username, BillingAddress billingAddress, ShippingAddress shippingAddress) {
        this.customerUserName = username;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;

    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerUserName() {
        return this.customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public List<ShippingAddress> getShippingList() {
        return ShippingList;
    }

    public void setShippingList(List<ShippingAddress> shippingList) {
        ShippingList = shippingList;
    }

    public List<Payment> getPaymentList() {
        return PaymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        PaymentList = paymentList;
    }


}
