package com.example.demo.productApi.service;

import com.example.demo.productApi.exception.ProductNotFoundException;
import com.example.demo.productApi.exception.SpFailedException;
import com.example.demo.productApi.model.Product;
import com.example.demo.productApi.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getAllProducts() throws SpFailedException {
        return productDao.getAllProducts();
    }

    public Optional<Product> getProductById(Long id) throws SpFailedException {
        return productDao.getProductById(id);
    }

    public Product createProduct(Product product) throws SpFailedException {
        validateProduct(product);
        return productDao.createProduct(product);
    }

    public Product updateProduct(Long id, Product product) throws SpFailedException {
        validateId(id);
        validateProduct(product);
        return productDao.updateProduct(id, product);
    }

    public void deleteProduct(Long id) throws SpFailedException {
        validateId(id);
        productDao.deleteProduct(id);
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new ValidationException("Product name cannot be null or empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new ValidationException("Product price must be positive");
        }
    }

    private void validateId(Long id) throws SpFailedException {
        Optional<Product> product1 = getProductById(id);
        if (product1.isEmpty()) {
            throw new ProductNotFoundException("No Such Product Found.");
        }
    }
}
