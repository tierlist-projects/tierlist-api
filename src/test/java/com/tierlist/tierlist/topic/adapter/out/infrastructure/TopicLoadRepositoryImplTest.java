package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.global.support.repository.RepositoryTest;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
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
class TopicLoadRepositoryImplTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TopicFavoriteRepositoryImpl topicFavoriteRepositoryImpl;

  private PasswordEncoder passwordEncoder;
  @Autowired
  private TopicLoadRepositoryImpl topicLoadRepositoryImpl;

  @BeforeEach
  void init() {
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Test
  void 즐겨찾기한_토픽을_가져올_수_있다() {
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

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .categoryId(category1.getId())
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .categoryId(category2.getId())
        .build());

    topicFavoriteRepositoryImpl.save(TopicFavorite.builder()
        .memberId(member.getId())
        .topicId(topic1.getId())
        .build());

    topicFavoriteRepositoryImpl.save(TopicFavorite.builder()
        .memberId(member.getId())
        .topicId(topic3.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadFavoriteTopics(member.getEmail(),
        PageRequest.of(0, 3));

    assertThat(topics.getContent()).hasSize(2);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic1.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic3.getId());
  }

  @Test
  void 이메일_없이_전체_토픽_목록을_가나다순으로_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .categoryId(category1.getId())
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category2.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadTopics(null, null,
        PageRequest.of(0, 3), null, TopicFilter.NONE);

    assertThat(topics.getContent()).hasSize(3);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic3.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic2.getId());
    assertThat(topics.getContent().get(2).getId()).isEqualTo(topic1.getId());
  }

  @Test
  void 이메일_없이_전체_토픽_목록을_즐겨찾기_많은_순으로_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .categoryId(category1.getId())
        .favoriteCount(1)
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category2.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadTopics(null, null,
        PageRequest.of(0, 3), null, TopicFilter.HOT);

    assertThat(topics.getContent()).hasSize(3);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic2.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic3.getId());
    assertThat(topics.getContent().get(2).getId()).isEqualTo(topic1.getId());
  }

  @Test
  void 이메일_없이_카테고리_내_토픽_목록을_가나다순으로_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .categoryId(category1.getId())
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category2.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadTopics(null, category1.getId(),
        PageRequest.of(0, 3), null, TopicFilter.NONE);

    assertThat(topics.getContent()).hasSize(2);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic2.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic1.getId());
  }

  @Test
  void 이메일_없이_카테고리_내_토픽_목록을_즐겨찾기_많은_순으로_가져올_수_있다() {

    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());
    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .favoriteCount(2)
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .favoriteCount(1)
        .categoryId(category1.getId())
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category2.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadTopics(null, category1.getId(),
        PageRequest.of(0, 3), null, TopicFilter.HOT);

    assertThat(topics.getContent()).hasSize(2);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic1.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic2.getId());
  }

  @Test
  void 전체_토픽_목록을_가나다순으로_가져올_수_있다() {
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

    Topic topic1 = topicRepository.save(Topic.builder()
        .name("토픽3")
        .categoryId(category1.getId())
        .build());

    Topic topic2 = topicRepository.save(Topic.builder()
        .name("토픽2")
        .favoriteCount(1)
        .categoryId(category1.getId())
        .build());

    Topic topic3 = topicRepository.save(Topic.builder()
        .name("토픽1")
        .categoryId(category2.getId())
        .build());

    topicFavoriteRepositoryImpl.save(TopicFavorite.builder()
        .memberId(member.getId())
        .topicId(topic2.getId())
        .build());

    Page<TopicResponse> topics = topicLoadRepositoryImpl.loadTopics(member.getEmail(), null,
        PageRequest.of(0, 3), null, TopicFilter.NONE);

    assertThat(topics.getContent()).hasSize(3);
    assertThat(topics.getContent().get(0).getId()).isEqualTo(topic3.getId());
    assertThat(topics.getContent().get(1).getId()).isEqualTo(topic2.getId());
    assertThat(topics.getContent().get(2).getId()).isEqualTo(topic1.getId());
  }
}