package com.eCommerece.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerece.major.model.Category;

public interface categoryRepository extends JpaRepository<Category,Integer> {

}
