package com.example.demo.Service;

import com.example.demo.Models.Category;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository _categoryRepo;
    private final ProductRepository _productRepo;
    //Get All Category
    public List<Category> getAllCategory(){
        return _categoryRepo.findAll();
    }
    //Get CategoryID
    public Optional<Category> getCategoryById(Long id){
        return _categoryRepo.findById(id);
    }
    //Add Category
    public Category addCategory(Category newCategory){
        return _categoryRepo.save(newCategory);
    }
    //Delete Category
    public void deleteCategory(Long id){
        var products = _productRepo.findAllByCategoryId(id);
        if (products != null){
            _productRepo.deleteAll(products);
        }
        _categoryRepo.deleteById(id);
    }
    //Update Category
    public void updateCategory(@NotNull Category category){
        Category currentCategory = _categoryRepo.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + "does not exists !!!"));
        currentCategory.setName(category.getName());
        _categoryRepo.save(currentCategory);
    }
}
