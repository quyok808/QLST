package com.example.demo.Controllers;

import com.example.demo.Models.Category;
import com.example.demo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("v1/api/categories")
public class CategoryAPIController {
    @Autowired
    private CategoryService _categoryService;

    @GetMapping
    public List<Category> getAllCategory(){
        return _categoryService.getAllCategory();
    }
    @PostMapping
    public Category createCate(@RequestBody Category newCategory) {
        return _categoryService.addCategory(newCategory);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category currentCategory = _categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found on :: " + id));
        currentCategory.setName(category.getName());
        final Category updatedCategory = _categoryService.addCategory(currentCategory); //Save
        return ResponseEntity.ok(updatedCategory);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCate(@PathVariable Long id) {
        Category cate = _categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found on :: "
                        + id));
        _categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
