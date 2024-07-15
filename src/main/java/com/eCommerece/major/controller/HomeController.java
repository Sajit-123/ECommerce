package com.eCommerece.major.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eCommerece.major.global.GlobalData;
import com.eCommerece.major.services.categoryService;
import com.eCommerece.major.services.productService;

@Controller
public class HomeController {

    private categoryService catServe;
    private productService proServe;

    public HomeController(categoryService catServe, productService proServe) {
        this.catServe = catServe;
        this.proServe = proServe;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String displayCategoryAndProductsOnShop(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("categories", catServe.getAllCategories());
        model.addAttribute("products", proServe.getAllProducts());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable int id,Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("categories", catServe.getAllCategories());
        model.addAttribute("products", proServe.getAllProductsByCategoryId(id));
        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id,Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("product", proServe.getProductById(id).get());
        return "viewProduct";
    }

    
}
