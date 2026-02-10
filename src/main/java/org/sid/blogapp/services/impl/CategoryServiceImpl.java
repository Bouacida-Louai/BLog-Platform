package org.sid.blogapp.services.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.repositories.CategoryRepository;
import org.sid.blogapp.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private  final CategoryRepository categoryRepository;
    @Override
    @Transactional
    public List<Category> listCategories() {
        List<Category> categories = categoryRepository.findAllByPostCount();
        return categories;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        String CategoryName=category.getName();
        if ( categoryRepository.existsByNameIgnoreCase(CategoryName)){
            throw new IllegalArgumentException("Category with name '"+category.getName()+"' already exists");
        }
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("Category with id '"+id+"' does not exist");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Category with id '"+id+"' does not exist")
        );
        return category;
    }


}
