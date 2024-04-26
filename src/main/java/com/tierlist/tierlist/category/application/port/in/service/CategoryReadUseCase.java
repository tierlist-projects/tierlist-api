package com.tierlist.tierlist.category.application.port.in.service;

import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.global.common.response.PageResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryReadUseCase {

  List<CategoryResponse> getCategories(int pageCount, int pageSize, String query,
      CategoryFilter filter);

  PageResponse<CategoryResponse> getFavoriteCategories(String email, Pageable pageable);

}
