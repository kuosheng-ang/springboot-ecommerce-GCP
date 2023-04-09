package com.clementang.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class ShoppingCartApp {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApp.class, args);
	}
}
