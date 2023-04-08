package com.clementang.shoppingcart.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "billing_address")
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String zipCode;


    private String customerUserName;

    @OneToOne(cascade=CascadeType.ALL)
    private Customer customer;


    public BillingAddress(Customer customer, String streetName, String city, String state, String country, String zipCode) {

        this.customer = customer;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.country= country;
        this.zipCode = zipCode;
    }


    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
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


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UUID getBillingAddressId() {
        return id;
    }

    public void setBillingAddressId(UUID billingAddressId) {
        this.id = billingAddressId;
    }

    @Override
    public String toString() {
        return "BillingAddress{" +
                "streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

}
