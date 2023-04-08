package com.clementang.shoppingcart.controllers;

//import com.clementang.shoppingcart.model.Page;
import com.clementang.shoppingcart.model.*;
//import org.jetbrains.annotations.NotNull;
import com.clementang.shoppingcart.repository.PageRepository;
import com.clementang.shoppingcart.repository.ProductRepository;
import com.clementang.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;

import javax.management.RuntimeOperationsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class PagesController {
    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private ProductRepository productRepo;

//    @Autowired
//    private UserService _userService;
//    private static final int INITIAL_PAGE = 0;

    @Autowired
    private UserRepository userRepository;


    public PagesController() {
    }


    @GetMapping
    public String home(Model model) {
        Page page = pageRepo.findBySlug("home");
        model.addAttribute("page", page);
        List<Product> productList = productRepo.findAll();
        model.addAttribute("productList", productList);
        //return principal != null ? "cart" : "page";
        return "page";

    }

    /*@GetMapping("/home")
    //@GetMapping("/")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = productService.findAllProductsPageable(PageRequest.of(evalPage, 5));
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "/login";
    }*/


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

      //  session.getAttribute("cart") != null
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login";
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute("cart");

        return "login";
    }

    /*private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }*/

    @GetMapping("/{slug}")
    public String page(@PathVariable String slug, Model model) {
        Page page = pageRepo.findBySlug(slug);
        if (page == null) {
            return "redirect:/";
        }
        model.addAttribute("page", page);
        return "page";
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,  Model model, Principal principal)
    {
        /*if(error != null){
            model.addAttribute("error", "Invalid username and password");
        }

        if (logout !=null){
            model.addAttribute("loginStatus", "You have been logged out successfully");
        }*/

        //System.out.println(request.getParameter("username"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        String pwd = request.getParameter("password");
        String username = request.getParameter("username");
        ///UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //System.out.println("User has authorities: " + userDetails.getAuthorities());

        User currentUser = userRepository.findByUsername(username);
        if (username == null || pwd == null)  {

            result.put("info", "Empty values. Please try again");
            result.put("code", "404");
            result.put("data", "credential error");
            model.addAttribute("loginStatus", "Missing or empty valid. Please try again");

        }

        if (currentUser.getPassword().equals(pwd) && currentUser.getUsername().equals(username)) {

            model.addAttribute("loginStatus", "login successful");
            result.put("info","login success");
            result.put("code","200");
            result.put("data","success");

            /*if (principal.getName().equals("admin")){
                System.out.println("admin login");
                return "redirect:/admin/pages";
            }
            User currentUserValidate
                    = (User) ((Authentication) principal).getPrincipal();*/
            model.addAttribute("username", currentUser.getUsername());

            //return "redirect:/cart";
            //return "cart";
            return "redirect:/customer/profile";
        }
        else  {
            model.addAttribute("loginStatus", "Invalid credentials, Please try again");
            result.put("info", "pwd error");
            result.put("code", "202");


        }

        return "login";
        //String json=JSON.toJSON(result).toString();
        //return json;
        //return "redirect:/login";
    }
}
