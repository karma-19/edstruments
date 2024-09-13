package com.example.demo.productApi.controller;

import com.example.demo.productApi.exception.ProductNotFoundException;
import com.example.demo.productApi.exception.SpFailedException;
import com.example.demo.productApi.model.Product;
import com.example.demo.productApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() throws SpFailedException {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws SpFailedException {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            throw new ProductNotFoundException("No such product found.");
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws SpFailedException {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) throws SpFailedException {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws SpFailedException {

        productService.deleteProduct(id);

        return ResponseEntity.notFound().build();
    }
}
