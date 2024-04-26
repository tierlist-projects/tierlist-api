package com.tierlist.tierlist.category.application.port.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryLoadRepository {

  Page<Category> loadFavoriteCategories(String email, Pageable pageable);

}
