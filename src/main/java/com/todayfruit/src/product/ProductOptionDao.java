package com.todayfruit.src.product;


import com.todayfruit.src.product.model.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionDao extends JpaRepository<ProductOption, Long> {


}
