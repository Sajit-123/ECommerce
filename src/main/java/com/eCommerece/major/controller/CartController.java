package com.eCommerece.major.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eCommerece.major.global.GlobalData;
import com.eCommerece.major.model.Products;
import com.eCommerece.major.services.productService;

@Controller
public class CartController {

    private productService proServe;

    public CartController(productService proServe) {
        this.proServe = proServe;
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id ){
        GlobalData.cart.add(proServe.getProductById((long) id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Products::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartRemoveItem(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Products::getPrice).sum());
        return "checkout";
    }

}
