package com.eCommerece.major.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eCommerece.major.global.GlobalData;
import com.eCommerece.major.model.Role;
import com.eCommerece.major.model.User;
import com.eCommerece.major.repository.RoleRepository;
import com.eCommerece.major.repository.UserRepository;

@Controller
public class LoginController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public LoginController(BCryptPasswordEncoder bCryptPasswordEncoder,UserRepository userRepository,RoleRepository roleRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder ;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }

    @PostMapping("/register")
    public String postUser(@ModelAttribute("user") User user,HttpServletRequest httpServletRequest ) throws ServletException{
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        httpServletRequest.login(user.getEmail(), password);
        return "redirect:/";
    }

}
