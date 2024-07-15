package com.eCommerece.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerece.major.dto.ProductDTO;
import com.eCommerece.major.model.Category;
import com.eCommerece.major.model.Products;
import com.eCommerece.major.services.categoryService;
import com.eCommerece.major.services.productService;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/ECommereceProject/src/main/resources/static/productImages";

    private categoryService catServe;
    private productService proServe;

    public AdminController(categoryService catServe,productService proServe){
        this.catServe = catServe;
        this.proServe = proServe;
    }
    //Category Section
    @GetMapping("/admin")
    public String AdminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories", catServe.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategories(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }
    @PostMapping("/admin/categories/add")
    public String postCategories(@ModelAttribute("category") Category category){
        catServe.addCategories(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("/admin/categories/delete/{id}")
    public String deleCategory(@PathVariable int id){
        catServe.delCategoryById(id);
        return "redirect:/admin/categories";
    }
    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id,Model model){
        Optional<Category> category = catServe.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        else{
            return "404";
        }
    }

    // Product section

    @GetMapping("/admin/products")
    public String getAllProducts(Model model){
        model.addAttribute("products", proServe.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProducts(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", catServe.getAllCategories());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProductPostMethod(@ModelAttribute("productDTO") ProductDTO productDTO,
                                        @RequestParam("productImage")MultipartFile file,
                                        @RequestParam("imgName")String imgName) throws IOException{
       
        Products products = new Products();
        products.setId(productDTO.getId());
        products.setName(productDTO.getName());
        products.setCategory(catServe.getCategoryById(productDTO.getCategoryId()).get());
        products.setPrice(productDTO.getPrice());
        products.setWeight(productDTO.getWeight());
        products.setDescription(productDTO.getDescription());
        String imageUUID;

        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path filePathAndName = Paths.get(uploadDir,imageUUID);
            Files.write(filePathAndName, file.getBytes());
        } else {
            imageUUID = imgName;
        }

        products.setImageName(imageUUID);
        proServe.addProduct(products);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleProduct(@PathVariable long id){
        proServe.delProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id,Model model){
        Products products = proServe.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(products.getId());
        productDTO.setName(products.getName());
        productDTO.setCategoryId(products.getCategory().getId()); 
        productDTO.setPrice(productDTO.getPrice());
        productDTO.setWeight(products.getWeight());
        productDTO.setDescription(products.getDescription());
        productDTO.setImageName(products.getImageName());

        model.addAttribute("categories", catServe.getAllCategories());
        model.addAttribute("productDTO", productDTO);
        

        return "productsAdd";
    }

}
