package org.sid.blogapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.dtos.CategoryDto;
import org.sid.blogapp.domain.dtos.CreateCategoryRequest;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.mappers.CategoryMapper;
import org.sid.blogapp.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper  categoryMapper;

    @GetMapping()
    public ResponseEntity<List<CategoryDto>>  listCategories(){
    List<CategoryDto> categories=categoryService.listCategories()
            .stream().map(category -> categoryMapper.toDto(category))
            .toList();
    return  ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody
                   CreateCategoryRequest categoryRequest){
       Category category=categoryMapper.toEntity(categoryRequest);
       Category savedCategory=categoryService.createCategory(category);
       CategoryDto categoryDto=categoryMapper.toDto(savedCategory);
         return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
