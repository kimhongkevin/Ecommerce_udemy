package com.app.ecom.repository;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("select p from product_table p where p.active = true and p.stockQuantity > 0 and lower(p.name) like lower(concat('%', :keyword , '%') )")
    List<Product> searchProducts(@Param("keyword") String keyword);

}
