package com.example.demo.Controllers;


import com.example.demo.Models.Product;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/products")
public class ProductAPIController {
    @Autowired
    private ProductService _productService;

    @GetMapping
    public List<Product> getAllProduct(){
        return _productService.getAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product newProduct){
        return _productService.addProduct(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> removeProduct (@PathVariable Long id)
    {
        Product currentProduct = _productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        _productService.deleteProduct(currentProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct (@PathVariable Long id,
                                                @RequestBody Product changedProduct)
    {
        Product currentProduct = _productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        currentProduct.setName(changedProduct.getName());
        currentProduct.setPrice(changedProduct.getPrice());
        currentProduct.setDescription(changedProduct.getDescription());
        currentProduct.setImage(changedProduct.getImage());
        currentProduct.setQuantity(changedProduct.getQuantity());
        currentProduct.setCategoryId(changedProduct.getCategoryId());

        final Product updateProduct = _productService.addProduct(currentProduct);
        return ResponseEntity.ok(updateProduct);
    }
}
