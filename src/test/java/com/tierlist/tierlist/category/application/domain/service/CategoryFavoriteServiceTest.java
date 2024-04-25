package com.tierlist.tierlist.category.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryFavoriteRepository;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryFavoriteRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryFavoriteServiceTest {

  private MemberRepository memberRepository;
  private CategoryRepository categoryRepository;
  private CategoryFavoriteRepository categoryFavoriteRepository;
  private CategoryFavoriteService categoryFavoriteService;

  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    memberRepository = new FakeMemberRepository();
    categoryRepository = new FakeCategoryRepository();
    categoryFavoriteRepository = new FakeCategoryFavoriteRepository();
    categoryFavoriteService = new CategoryFavoriteService(memberRepository, categoryRepository,
        categoryFavoriteRepository);

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Test
  void 카테고리_즐겨찾기를_생성할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("nickname")
        .password(Password.fromRawPassword("testpassword", passwordEncoder))
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    // when
    categoryFavoriteService.toggleFavorite(member.getEmail(), category.getId());
    // then
    Optional<CategoryFavorite> categoryFavoriteOptional = categoryFavoriteRepository.findByMemberIdAndCategoryId(
        member.getId(), category.getId());

    assertThat(categoryFavoriteOptional).isPresent();
    assertThat(categoryFavoriteOptional.get().getMemberId()).isEqualTo(member.getId());
    assertThat(categoryFavoriteOptional.get().getCategoryId()).isEqualTo(category.getId());
  }

  @Test
  void 카테고리_즐겨찾기를_삭제할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("nickname")
        .password(Password.fromRawPassword("testpassword", passwordEncoder))
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    CategoryFavorite categoryFavorite = categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category.getId())
        .build());

    // when
    categoryFavoriteService.toggleFavorite(member.getEmail(), category.getId());

    // then
    Optional<CategoryFavorite> categoryFavoriteOptional = categoryFavoriteRepository.findByMemberIdAndCategoryId(
        member.getId(), category.getId());

    assertThat(categoryFavoriteOptional).isEmpty();
  }

  @Test
  void 존재하지_않는_카테고리에_즐겨찾기를_토글_할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("nickname")
        .password(Password.fromRawPassword("testpassword", passwordEncoder))
        .build());

    // when
    // then
    String memberEmail = member.getEmail();
    assertThatThrownBy(() -> {
      categoryFavoriteService.toggleFavorite(memberEmail, 1L);
    }).isInstanceOf(CategoryNotFoundException.class);
  }

}