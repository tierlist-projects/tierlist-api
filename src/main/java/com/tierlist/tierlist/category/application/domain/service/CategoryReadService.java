package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.CategoryReadUseCase;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryLoadRepository;
import com.tierlist.tierlist.global.common.response.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryReadService implements CategoryReadUseCase {

  private final CategoryLoadRepository categoryLoadRepository;

  @Override
  public List<CategoryResponse> getCategories(int pageCount, int pageSize, String query,
      CategoryFilter filter) {
    return List.of();
  }

  @Override
  public PageResponse<CategoryResponse> getFavoriteCategories(String email, Pageable pageable) {
    Page<Category> categories = categoryLoadRepository.loadFavoriteCategories(email, pageable);
    return PageResponse.fromPage(categories.map(CategoryResponse::from));
  }
}
