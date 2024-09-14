package com.example.demo.Service;


import com.example.demo.Models.Product;
import com.example.demo.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository _productRepository;

    //Get All Product
    public List<Product> getAll() {
        return _productRepository.findAll();
    }

    //Add new Product
    public Product addProduct(Product newProduct) {
        return _productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(Long id) {
        return _productRepository.findById(id);
    }

    public void deleteProduct(Product currentProduct) {
        _productRepository.delete(currentProduct);
    }
}
