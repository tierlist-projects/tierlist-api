package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.CategoryReadUseCase;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryLoadRepository;
import com.tierlist.tierlist.global.common.response.PageResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryReadService implements CategoryReadUseCase {

  private final CategoryLoadRepository categoryLoadRepository;

  @Override
  public PageResponse<CategoryResponse> getCategories(String email, Pageable pageable, String query,
      CategoryFilter filter) {
    return PageResponse.fromPage(
        categoryLoadRepository.loadCategories(email, pageable, query, filter));
  }

  @Override
  public PageResponse<CategoryResponse> getFavoriteCategories(String email, Pageable pageable) {
    Page<Category> categories = categoryLoadRepository.loadFavoriteCategories(email, pageable);
    return PageResponse.fromPage(categories.map(CategoryResponse::from));
  }

  @Override
  public CategoryResponse getCategory(String email, Long id) {
    CategoryResponse categoryResponse = categoryLoadRepository.loadCategoryById(email, id);

    if (Objects.isNull(categoryResponse)) {
      throw new CategoryNotFoundException();
    }

    return categoryResponse;
  }
}
