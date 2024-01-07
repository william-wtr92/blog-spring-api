package com.springboot.blog.repository;

import com.springboot.blog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " + "p.name LIKE CONCAT('%', :query, '%') OR " + "p.description LIKE CONCAT('%', :query, '%')")
    Page<Product> searchProducts(String query, Pageable pageable);

    // We can use in Repository native SQL query with: nativeQuery = true

    /* @Query(value = "SELECT * FROM products p WHERE " +
            "p.name LIKE CONCAT('%', :query, '%') OR " +
            "p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Product> searchProductsSQL(String query); */
}
