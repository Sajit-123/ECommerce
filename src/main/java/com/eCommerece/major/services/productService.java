package com.eCommerece.major.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eCommerece.major.model.Products;
import com.eCommerece.major.repository.productRepository;

@Service
public class productService {

    private productRepository prorep;

    public productService(productRepository prorep){
        this.prorep = prorep;
    }

    public void addProduct(Products product){
        prorep.save(product);
    }

    public List<Products> getAllProducts(){
        return prorep.findAll();
    }
        public void delProductById(long id){
        prorep.deleteById(id);
    }
    public Optional<Products> getProductById(Long id){
        return prorep.findById(id);
    }
    public List<Products> getAllProductsByCategoryId(int id){
        return prorep.findAllByCategory_Id(id);
    }


}
