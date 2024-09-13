package com.example.demo.productApi.repository;

import com.example.demo.productApi.exception.SpFailedException;
import com.example.demo.productApi.model.Product;
import com.example.demo.productApi.rowMapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;

    public ProductDao(JdbcTemplate jdbcTemplate, ProductRowMapper productRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRowMapper = productRowMapper;
    }

    public List<Product> getAllProducts() throws SpFailedException {
        try {
            return jdbcTemplate.query("SELECT * FROM products", new ProductRowMapper());
        } catch (Exception e) {
            throw new SpFailedException("Get all product sp failed.");
        }
    }

    public Optional<Product> getProductById(Long id) throws SpFailedException {
        try {
            return jdbcTemplate.query("SELECT * FROM products WHERE id = ?", new Object[]{id}, new ProductRowMapper()).stream().findFirst();
        } catch (Exception e) {
            throw new SpFailedException("Get product by id sp failed.");
        }
    }

    public Product createProduct(Product product) throws SpFailedException {
        try {
            jdbcTemplate.update("INSERT INTO products (name, description, price) VALUES (?, ?, ?)",
                    product.getName(), product.getDescription(), product.getPrice());
        } catch (Exception e) {
            throw new SpFailedException("Create product sp failed.");
        }
        return getProductById(product.getId()).orElse(null);
    }

    public Product updateProduct(Long id, Product product) throws SpFailedException {
        try {
            jdbcTemplate.update("UPDATE products SET name = ?, description = ?, price = ? WHERE id = ?",
                    product.getName(), product.getDescription(), product.getPrice(), id);
        } catch (Exception e) {
            throw new SpFailedException("Update product sp failed.");
        }
        return getProductById(id).orElse(null);
    }

    public void deleteProduct(Long id) throws SpFailedException {
        try {
            jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
        } catch (Exception e) {
            throw new SpFailedException("Delete product sp failed.");
        }
    }
}
