package org.sid.blogapp.services.impl;


import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.repositories.CategoryRepository;
import org.sid.blogapp.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


}
