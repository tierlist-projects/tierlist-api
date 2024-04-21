package com.tierlist.tierlist.category.application.port.in.service;

import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryReadUseCase {

  List<CategoryResponse> getCategories(int pageCount, int pageSize, String query,
      CategoryFilter filter);

  List<CategoryResponse> getFavoriteCategories(String email, int pageCount, int pageSize);

}
