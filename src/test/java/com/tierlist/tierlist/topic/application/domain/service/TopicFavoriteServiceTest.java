package com.tierlist.tierlist.topic.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.topic.FakeTopicFavoriteRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.topic.application.domain.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicFavoriteRepository;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TopicFavoriteServiceTest {

  private MemberRepository memberRepository;
  private TopicRepository topicRepository;
  private TopicFavoriteRepository topicFavoriteRepository;
  private TopicFavoriteService topicFavoriteService;

  private CategoryRepository categoryRepository;

  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    memberRepository = new FakeMemberRepository();
    topicRepository = new FakeTopicRepository();
    topicFavoriteRepository = new FakeTopicFavoriteRepository();
    topicFavoriteService = new TopicFavoriteService(memberRepository, topicRepository,
        topicFavoriteRepository);

    categoryRepository = new FakeCategoryRepository();

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

    Topic topic = topicRepository.save(Topic.builder()
        .categoryId(1L)
        .name("토픽")
        .build());

    // when
    topicFavoriteService.toggleFavorite(member.getEmail(), topic.getId());

    // then
    Optional<TopicFavorite> topicFavoriteOptional = topicFavoriteRepository
        .findByMemberIdAndTopicId(member.getId(), topic.getId());
    Optional<Topic> topicOptional = topicRepository.findById(topic.getId());

    assertThat(topicFavoriteOptional).isPresent();
    assertThat(topicFavoriteOptional.get().getMemberId()).isEqualTo(member.getId());
    assertThat(topicFavoriteOptional.get().getTopicId()).isEqualTo(topic.getId());
    assertThat(topicOptional).isPresent();
    assertThat(topicOptional.get().getFavoriteCount()).isEqualTo(1);
  }

  @Test
  void 토픽_즐겨찾기를_삭제할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("nickname")
        .password(Password.fromRawPassword("testpassword", passwordEncoder))
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .categoryId(1L)
        .favoriteCount(1)
        .name("토픽")
        .build());

    topicFavoriteRepository.save(TopicFavorite.builder()
        .memberId(member.getId())
        .topicId(topic.getId())
        .build());

    // when
    topicFavoriteService.toggleFavorite(member.getEmail(), topic.getId());

    // then
    Optional<TopicFavorite> topicFavoriteOptional = topicFavoriteRepository
        .findByMemberIdAndTopicId(member.getId(), topic.getId());
    Optional<Topic> topicOptional = topicRepository.findById(topic.getId());

    assertThat(topicFavoriteOptional).isEmpty();
    assertThat(topicOptional).isPresent();
    assertThat(topicOptional.get().getFavoriteCount()).isZero();
  }

  @Test
  void 존재하지_않는_토픽에_즐겨찾기를_토글_할_수_없다() {
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
      topicFavoriteService.toggleFavorite(memberEmail, 1L);
    }).isInstanceOf(TopicNotFoundException.class);
  }

}