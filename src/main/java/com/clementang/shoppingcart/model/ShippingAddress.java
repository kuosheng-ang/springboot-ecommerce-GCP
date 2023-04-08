package com.clementang.shoppingcart.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetName;
    private String apartmentNumber;
    private String city;
    private String state;

    private String country;
    private String zipCode;

    private String customerUserName;

    private boolean customerShippingDefault;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public ShippingAddress(String username, String streetName, String apartmentNumber, String city, String state, String country, String zipCode) {

        this.customerUserName = username;
        this.streetName = streetName;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.state = state;
        this.country= country;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getShippingAddressId() {
        return id;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.id = shippingAddressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isCustomerShippingDefault() {
        return customerShippingDefault;
    }

    public void setCustomerShippingDefault(boolean customerShippingDefault) {
        this.customerShippingDefault = customerShippingDefault;
    }

}
