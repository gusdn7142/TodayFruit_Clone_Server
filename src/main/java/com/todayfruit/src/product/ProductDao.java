package com.todayfruit.src.product;



import com.todayfruit.src.product.model.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductDao extends JpaRepository<Product, Long> {




}
