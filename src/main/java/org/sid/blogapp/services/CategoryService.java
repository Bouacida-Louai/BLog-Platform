package org.sid.blogapp.services;


import org.sid.blogapp.domain.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryService {

    List<Category> listCategories();

}
