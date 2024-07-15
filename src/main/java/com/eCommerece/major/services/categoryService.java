package com.eCommerece.major.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eCommerece.major.model.Category;
import com.eCommerece.major.repository.categoryRepository;

@Service
public class categoryService {

    private categoryRepository rep;

    public categoryService(categoryRepository rep){
        this.rep = rep;
    }

    public void addCategories(Category category){
        rep.save(category);
    }
    public List<Category> getAllCategories(){
        return rep.findAll();
    }
    public void delCategoryById(int id){
        rep.deleteById(id);
    }
    public Optional<Category> getCategoryById(int id){
        return rep.findById(id);
    }
    
}
