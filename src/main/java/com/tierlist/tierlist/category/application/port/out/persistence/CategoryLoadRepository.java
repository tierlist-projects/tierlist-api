package com.tierlist.tierlist.category.application.port.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryLoadRepository {

  Page<Category> loadFavoriteCategories(String email, Pageable pageable);

  Page<CategoryResponse> loadCategories(String email, Pageable pageable, String query,
      CategoryFilter filter);
}
