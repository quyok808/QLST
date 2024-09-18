package com.example.demo.Controllers;


import com.example.demo.Models.Category;
import com.example.demo.Models.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/products")
public class ProductAPIController {
    @Autowired
    private ProductService _productService;
    @Autowired
    private CategoryService _categoryService;
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping
    public List<Product> getAllProduct(){
        return _productService.getAll();
    }

    @PostMapping
    public Product createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("category") Long categoryId,
            @RequestPart("image") MultipartFile image) throws Exception {

        // Create a new Product object
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setQuantity(quantity);
        Optional<Category> newCateProduct = _categoryService.getCategoryById(categoryId);
        newProduct.setCategory(newCateProduct.get());
        // Handle the image upload
        if (image != null && !image.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            String orgName = image.getOriginalFilename();
            String uniqueFileName = orgName;
            Path filePath = uploadPath.resolve(uniqueFileName);
            image.transferTo(filePath.toFile());
            newProduct.setImage(uniqueFileName);
        }

        // Save the product
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
        currentProduct.getCategory().setId(changedProduct.getCategory().getId());

        final Product updateProduct = _productService.addProduct(currentProduct);
        return ResponseEntity.ok(updateProduct);
    }
}
