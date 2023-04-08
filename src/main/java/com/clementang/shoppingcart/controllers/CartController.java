package com.clementang.shoppingcart.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import com.clementang.shoppingcart.model.Cart;
import com.clementang.shoppingcart.model.Product;
import com.clementang.shoppingcart.model.User;
import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private  ShoppingCartService _shoppingCartService;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private  ProductService _productService;
    @Autowired
    private  UserService _userService;


    /*@GetMapping("/view")
    //@RequestMapping("/view")
    public ModelAndView shoppingCart(HttpSession session, Model model) {
        //ModelAndView modelAndView = new ModelAndView("/shoppingCart");

        ModelAndView modelAndView = new ModelAndView("/view");
        modelAndView.addObject("products", _shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", _shoppingCartService.getTotal().toString());

        if (session.getAttribute("cart") == null) {   // add on 23 Dec 2022
            return modelAndView;
        }
        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("notCartViewPage", true);

        return modelAndView;
    }

    @GetMapping("/cart/add/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        _productService.findByProductId(productId).ifPresent(_shoppingCartService::addProduct);
        return shoppingCart();
    }

    @GetMapping("/cart/remove/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        _productService.findByProductId(productId).ifPresent(_shoppingCartService::removeProduct);
        return shoppingCart();
    }

    @GetMapping("/cart/checkout")
    public ModelAndView checkout() {
        try {
            _shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }*/

    @RequestMapping("/add/{id}")
    public String add(@PathVariable UUID id, HttpSession session, Model model, @RequestParam(value = "cartPage", required = false) String cartPage, Principal principal) {

        boolean cartActive = false;
        if (principal == null)
            return "redirect:/login";

        else{

            Product product = productRepo.findById(id);
            if (session.getAttribute("cart") == null) {
                Map<UUID, Cart> cart = new HashMap<>();
                /** for digital products, quantity increment by 1 when adding into add **/
                cart.put(id, new Cart(id, product.getNamedTitle(), product.getPrice(), product.getProductImage(), 1, principal.getName()));
                session.setAttribute("cart", cart);

            } else {
                /** Setting or assigning the session with Cart attribute so that it keeps tracks of all the properties - cartQty **/
                Map<UUID, Cart> cart = (Map<UUID, Cart>) session.getAttribute("cart");
                if (cart.containsKey(id)) {
                    /*Map<Long, Cart> cart = (Map<Long, Cart>) session.getAttribute("cart");*/
                    int qty = cart.get(id).getCartQuantity();
                    cart.put(id, new Cart(id, product.getNamedTitle(), product.getPrice(), product.getProductImage(), ++qty, principal.getName()));
                    session.setAttribute("cart", cart);
                }else {
                    cart.put(id, new Cart(id, product.getNamedTitle(), product.getPrice(), product.getProductImage(), 1, principal.getName()));
                    session.setAttribute("cart", cart);
                }
            }
            Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
            int size = 0;
            double totalCost = 0;
            for (Cart value : cart.values()) {
                size += value.getCartQuantity();
                totalCost += value.getCartQuantity() * value.getCartPrice();
                //totalCost = totalCost.add(value.getCartPrice().multiply(BigDecimal.valueOf(value.getCartQuantity())));
                _shoppingCartService.addToCart(value);
                cartActive = true;
            }
            model.addAttribute("csize", size);
            model.addAttribute("ctotal", totalCost);

            if (cartPage != null) {
                //return "redirect:/cart/view";
                return "cart";

            }
            cartActive = true;
            model.addAttribute("cartActive", cartActive);
            model.addAttribute("cart", cart);
            model.addAttribute("notCartViewPage", true);

        }
        return "redirect:/cart/view";

    }


    @RequestMapping("/view")
    public String view(HttpSession session, Model model) {

        boolean cartActive = false;
        if (session.getAttribute("cart") == null) {

            return "redirect:/";
        }
        else {

            Map<UUID, Cart> cart = (Map<UUID, Cart>) session.getAttribute("cart");
            cartActive = true;
            model.addAttribute("cart", cart);
            /*model.addAttribute("notCartViewPage", true);*/  // dont remove this - remains commented
            model.addAttribute("cartActive", cartActive);
        }
        return "cartItem";
    }

    @RequestMapping("/subtract/{id}")
    public String subtract(@PathVariable UUID id, HttpSession session, Model model, HttpServletRequest httpServletRequest, Principal principal) {
        Product product = productRepo.findById(id);

        Map<UUID, Cart> cart = (Map<UUID, Cart>) session.getAttribute("cart");
        //User cartUser = _userService.findByUsername(httpServletRequest.getParameter("username"));

        int qty = cart.get(id).getCartQuantity();
        if (qty == 1) {
            cart.remove(id);
            if (cart.size() == 0) {
                session.removeAttribute("cart");
            }
        } else {
            cart.put(id, new Cart(id, product.getNamedTitle(), product.getPrice(), product.getProductImage(), --qty, principal.getName()));
        }
        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable UUID id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {
        
        Map<UUID, Cart> cart = (Map<UUID, Cart>) session.getAttribute("cart");
        cart.remove(id);
        if (cart.size() == 0) {
            session.removeAttribute("cart");
        }

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }

    @GetMapping("/clear")
    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {
        session.removeAttribute("cart");

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }


}
