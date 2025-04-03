package com.example.demo.Controllers;


import com.example.demo.Models.Category;
import com.example.demo.Models.CustomUserDetails;
import com.example.demo.Models.Product;
import com.example.demo.Models.User;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
//    public ResponseEntity<List<Product>> getAllProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        System.out.println("Authenticated user: lalalalala");
//        if (customUserDetails == null) {
//            // Xử lý trường hợp không có người dùng nào được xác thực
//            System.out.println("Authenticated user: lalalalala");
//            return null;
//        }
//        System.out.println("Authenticated user: " + customUserDetails.getUsername());
//        // Lấy thông tin người dùng từ customUserDetails
//        List<Product> products = _productService.getAll();
//
//        return ResponseEntity.ok(products);
//    }

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
                                                @RequestParam("name") String name,
                                                @RequestParam("price") Double price,
                                                @RequestParam("description") String description,
                                                @RequestParam("quantity") Integer quantity,
                                                @RequestParam("category") Long categoryId,
                                                @RequestPart(value = "image", required = false) MultipartFile image) throws Exception
    {
        Product currentProduct = _productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        currentProduct.setName(name);
        currentProduct.setPrice(price);
        currentProduct.setDescription(description);
        currentProduct.setQuantity(quantity);
        Optional<Category> newCateProduct = _categoryService.getCategoryById(categoryId);
        currentProduct.setCategory(newCateProduct.get());
        if (image != null && !image.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            String orgName = image.getOriginalFilename();
            String uniqueFileName = orgName;
            Path filePath = uploadPath.resolve(uniqueFileName);
            image.transferTo(filePath.toFile());
            currentProduct.setImage(uniqueFileName);
        }

        final Product updateProduct = _productService.addProduct(currentProduct);
        return ResponseEntity.ok(updateProduct);
    }
}
