package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import com.tierlist.tierlist.category.application.port.in.service.CategoryFavoriteUseCase;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryFavoriteRepository;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryFavoriteService implements CategoryFavoriteUseCase {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryFavoriteRepository categoryFavoriteRepository;

  @Transactional
  @Override
  public void toggleFavorite(String email, Long categoryId) {

    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(CategoryNotFoundException::new);

    Optional<CategoryFavorite> categoryFavorite = categoryFavoriteRepository.findByMemberIdAndCategoryId(
        member.getId(), category.getId());

    if (categoryFavorite.isEmpty()) {
      categoryFavoriteRepository.save(CategoryFavorite.builder()
          .memberId(member.getId())
          .categoryId(category.getId())
          .build());
      return;
    }

    categoryFavoriteRepository.delete(categoryFavorite.get());

  }
}
