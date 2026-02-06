package org.sid.blogapp.services.impl;


import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.repositories.CategoryRepository;
import org.sid.blogapp.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private  final CategoryRepository categoryRepository;
    @Override
    public List<Category> listCategories() {

        List<Category> categories = categoryRepository.findAll();
        return  categories;

    }
}
