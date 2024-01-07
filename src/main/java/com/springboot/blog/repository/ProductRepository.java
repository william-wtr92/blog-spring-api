package com.springboot.blog.repository;

import com.springboot.blog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " + "p.name LIKE CONCAT('%', :query, '%') OR " + "p.description LIKE CONCAT('%', :query, '%')")
    List<Product> searchProducts(String query);

    // We can use in Repository native SQL query with: nativeQuery = true

    /* @Query(value = "SELECT * FROM products p WHERE " +
            "p.name LIKE CONCAT('%', :query, '%') OR " +
            "p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Product> searchProductsSQL(String query); */
}
