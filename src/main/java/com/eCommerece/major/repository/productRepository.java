package com.eCommerece.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerece.major.model.Products;

public interface productRepository extends JpaRepository<Products,Long> {

    List<Products> findAllByCategory_Id(int id);
}
