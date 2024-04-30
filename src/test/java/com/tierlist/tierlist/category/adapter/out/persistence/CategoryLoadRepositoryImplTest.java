package com.tierlist.tierlist.category.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryFavoriteRepository;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.global.support.repository.RepositoryTest;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@RepositoryTest
class CategoryLoadRepositoryImplTest {

  @Autowired
  private CategoryLoadRepositoryImpl categoryLoadRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CategoryFavoriteRepository categoryFavoriteRepository;

  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Test
  void 즐겨찾기한_카테고리를_가져올_수_있다() {
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());

    categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category1.getId())
        .build());
    categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category3.getId())
        .build());

    Page<Category> categories = categoryLoadRepository.loadFavoriteCategories(member.getEmail(),
        PageRequest.of(0, 3));

    assertThat(categories.getContent()).hasSize(2);
    assertThat(categories.getContent().get(0).getId()).isEqualTo(category1.getId());
    assertThat(categories.getContent().get(1).getId()).isEqualTo(category3.getId());
  }

  @Test
  void 카테고리_전체_목록을_가져올_때_이름순으로_가져온다() {
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category1.getId())
        .build());
    categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category3.getId())
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        member.getEmail(),
        PageRequest.of(0, 3), null, CategoryFilter.NONE);

    assertThat(categoryResponses.getContent()).hasSize(3);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category3.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category2.getId());
    assertThat(categoryResponses.getContent().get(2).getId()).isEqualTo(category1.getId());
  }

  @Test
  void 카테고리_전체_검색_목록을_가져올_때_이름순으로_가져온다() {
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리12")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리11")
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        member.getEmail(),
        PageRequest.of(0, 3), "1", CategoryFilter.NONE);

    assertThat(categoryResponses.getContent()).hasSize(2);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category2.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category1.getId());
  }

  @Test
  void 핫한_카테고리_전체_목록을_가져올_때_즐겨찾기_많은순_이름순으로_가져온다() {
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .favoriteCount(1)
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    categoryFavoriteRepository.save(CategoryFavorite.builder()
        .memberId(member.getId())
        .categoryId(category2.getId())
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        member.getEmail(),
        PageRequest.of(0, 3), null, CategoryFilter.HOT);

    assertThat(categoryResponses.getContent()).hasSize(3);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category2.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category3.getId());
    assertThat(categoryResponses.getContent().get(2).getId()).isEqualTo(category1.getId());
  }

  @Test
  void 핫한_카테고리_검색_목록을_가져올_때_즐겨찾기_많은순_이름순으로_가져온다() {
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리13")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리12")
        .favoriteCount(1)
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        member.getEmail(),
        PageRequest.of(0, 3), "1", CategoryFilter.HOT);

    assertThat(categoryResponses.getContent()).hasSize(2);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category2.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category1.getId());
  }

  @Test
  void 로그인없이_핫한_카테고리_목록을_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리13")
        .favoriteCount(1)
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리12")
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        null,
        PageRequest.of(0, 3), "1", CategoryFilter.HOT);

    assertThat(categoryResponses.getContent()).hasSize(2);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category1.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category2.getId());
  }

  @Test
  void 로그인없이_카테고리_목록을_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리13")
        .favoriteCount(1)
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리12")
        .build());
    Category category3 = categoryRepository.save(Category.builder()
        .name("카테고리3")
        .build());

    Page<CategoryResponse> categoryResponses = categoryLoadRepository.loadCategories(
        null,
        PageRequest.of(0, 3), "1", CategoryFilter.NONE);

    assertThat(categoryResponses.getContent()).hasSize(2);
    assertThat(categoryResponses.getContent().get(0).getId()).isEqualTo(category2.getId());
    assertThat(categoryResponses.getContent().get(1).getId()).isEqualTo(category1.getId());
  }
}