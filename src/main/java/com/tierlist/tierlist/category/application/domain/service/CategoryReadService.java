package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.CategoryReadUseCase;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryReadService implements CategoryReadUseCase {

  @Override
  public List<CategoryResponse> getCategories(int pageCount, int pageSize, String query,
      CategoryFilter filter) {
    return List.of();
  }

  @Override
  public List<CategoryResponse> getFavoriteCategories(String email, int pageCount, int pageSize) {
    return List.of();
  }
}
