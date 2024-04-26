package com.tierlist.tierlist.category.adapter.in.web;

import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.CategoryReadUseCase;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.global.common.response.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryReadController {

  private final CategoryReadUseCase categoryReadUseCase;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getCategories(
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam CategoryFilter filter) {
    return ResponseEntity.ok(categoryReadUseCase.getCategories(pageCount, pageSize, query, filter));
  }

  @GetMapping("/favorite")
  public ResponseEntity<PageResponse<CategoryResponse>> getFavoriteCategories(
      @AuthenticationPrincipal String email,
      Pageable pageable) {
    return ResponseEntity.ok(categoryReadUseCase.getFavoriteCategories(email, pageable));
  }

}
