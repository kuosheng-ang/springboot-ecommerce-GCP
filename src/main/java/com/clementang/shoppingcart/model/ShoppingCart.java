package com.clementang.shoppingcart.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="shoppingCart_id")
	private UUID id;
	private BigDecimal GrandTotal;
	
	@OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Cart> cartList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BigDecimal getGrandTotal() {
		return GrandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		GrandTotal = grandTotal;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartItemList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
