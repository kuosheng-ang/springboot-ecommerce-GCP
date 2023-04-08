package com.clementang.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
@NoArgsConstructor
@Table(name = "cart")  //- illegal use of @Table in a subclass of single_table hierarchy
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //@OneToOne(mappedBy = "cart")
    /*@JoinColumn(name = "user_id")
    @JsonProperty*/
    // @Column(s) not allowed on a @OneToOne property, Mapped by is allowed only for OneToMany & not ManyToOne
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "cart_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    // @Column(name = "username", nullable = false, updatable = true)  - @Column not allowed for ManyToOne
    private User user;*/



    /*@ManyToMany
            @JsonProperty
            @Column
            private Collection<Product> CartItems;*/
    @Column
    private UUID productId;
    @Column
    private String productTitle;
    @Column
    private int cartQuantity;

    @Column
    private Double cartPrice;

    private String cartImage;

    @Column
    private BigDecimal CartTotal;

    @Column
    private String customerName;

    @ManyToOne
    @JoinColumn(name="shoppingCart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;



    public Cart(UUID id, String title, Double price, String productImage, int quantity, String user) {
        this.productId = id;
        this.productTitle = title;
        this.cartPrice = price;
        this.cartImage = productImage;
        this.cartQuantity = quantity;
        this.customerName = user;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public void setCartPrice(Double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public BigDecimal getCartTotal() {
        return CartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        CartTotal = cartTotal;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public Double getCartPrice() {
        return this.cartPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductStockNumber(String productTitle) {
        this.productTitle = productTitle;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
