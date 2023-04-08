package com.clementang.shoppingcart.security;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clementang.shoppingcart.repository.*;
import com.clementang.shoppingcart.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.clementang.shoppingcart.model.Role;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/register")
public class RegistrationController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    /*@RequestMapping(value = "/register", method = RequestMethod.GET)
    @GetMapping
    public String register (User user) {
        return "register";
    }*/

    @GetMapping
    public String register (Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @RequestMapping(name ="/register", method = RequestMethod.POST)
    //public String register(@Valid User user, BindingResult bindingResult,  Model models )  {
    //@PostMapping
    public String register(@Valid @NotNull User user, BindingResult bindingResult, Model model )  {

            if (userRepository.findByUsername(user.getUsername()) != null) {

                model.addAttribute("userExist", "Username already exist");
                System.out.println("Username already exist");
                return "register";

                /*bindingResult
                        .rejectValue("username", "error.user",
                                "There is already a user registered with the username provided");*/
            }
            System.out.println(user.getPassword());
            System.out.println(user.getConfirmPassword());
            if (user.getPassword().equals(user.getConfirmPassword())) {

                //rolesForNewUser.add(new Role("ROLE_USER"));

                if (user.getUsername().equalsIgnoreCase("admin"))
                {
                    user.setRoles(Arrays.asList(roleRepository.findByRole("ROLE_ADMIN")));
                }
                else{
                    user.setRoles(Arrays.asList(roleRepository.findByRole("ROLE_USER")));
                }
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            }
            else if (!user.getPassword().equals(user.getConfirmPassword())){
                model.addAttribute("passwordMatchProblem", "Password do not match");
                System.out.println("Password do not match");
                return "register";
            }
            else if (bindingResult.hasErrors())  {
                System.out.println("Binding result error");
                return "register";
            }
            return "redirect:/login";

    }
        /*if (userRepository.findByEmail(user.getEmail()) != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
            return "register";
           // throw new Exception("There is already a user registered with the email provided");
        }


        /** Ensure password matching **/


        /*if (user.getPassword().equals(user.getConfirmPassword())) { //tested working for password matching
            model.addAttribute("passwordMatch", "Password match");

        }
        else{
            model.addAttribute("passwordNotMatch", "Password do not match");
            return "register";
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);*/


     /*@Autowired
    public RegistrationController(UserService userService) {
        this._userService = userService;
    }*/


    /*@RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();
        if (_userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (_userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");

        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            modelMap.addAttribute("passwordMatchProblem", "Password do not match");
            modelAndView.setViewName("/register");
            //return "register";
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/register");
        } else {
            // Registration successful, save user
            // Set user role to USER and set it as active

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            _userService.saveUser(user);
            modelAndView.setViewName("/login");
        }
        return modelAndView;
    } */



}
