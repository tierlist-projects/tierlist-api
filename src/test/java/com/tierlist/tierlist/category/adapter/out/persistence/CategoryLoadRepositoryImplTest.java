package com.tierlist.tierlist.category.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
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
}