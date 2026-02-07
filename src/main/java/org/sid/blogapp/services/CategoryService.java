package org.sid.blogapp.services;


import org.sid.blogapp.domain.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);

    void deleteCategory(UUID id);
}
