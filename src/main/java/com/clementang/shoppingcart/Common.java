package com.clementang.shoppingcart;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.clementang.shoppingcart.model.Cart;
import com.clementang.shoppingcart.repository.CategoryRepository;
import com.clementang.shoppingcart.repository.PageRepository;
import com.clementang.shoppingcart.model.Category;
import com.clementang.shoppingcart.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class Common {
    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {
        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();

        boolean cartActive = false;
        if (session.getAttribute("cart") != null) {
            Map<UUID, Cart> cart = (Map<UUID, Cart>) session.getAttribute("cart");
            int size = 0;
            double total = 0;

            for (Cart value : cart.values()) {
                size += value.getCartQuantity();
                total += value.getCartPrice() * value.getCartQuantity();
            }

            model.addAttribute("csize", size);
            model.addAttribute("ctotal", total);

            cartActive = true;
            model.addAttribute("cartActive", cartActive);
        }

        /*model.addAttribute("cpages", pages);*/

        model.addAttribute("ccategories", categories);

    }
}
