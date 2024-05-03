package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistLikeRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLikeRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TierlistLikeServiceTest {

  private MemberRepository memberRepository;
  private TierlistLikeRepository tierlistLikeRepository;
  private TierlistRepository tierlistRepository;

  private TierlistLikeService sut;

  private CategoryRepository categoryRepository;
  private TopicRepository topicRepository;

  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    memberRepository = new FakeMemberRepository();
    topicRepository = new FakeTopicRepository();
    categoryRepository = new FakeCategoryRepository();

    tierlistRepository = new FakeTierlistRepository();
    tierlistLikeRepository = new FakeTierlistLikeRepository();

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    sut = new TierlistLikeService(memberRepository, tierlistRepository, tierlistLikeRepository);
  }

  @Test
  void 티어리스트_좋아요를_생성할_수_있다() {
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

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .topicId(topic.getId())
        .memberId(member.getId())
        .title("티어리스트")
        .build());

    // when
    sut.toggleLike(member.getEmail(), tierlist.getId());

    // then
    Optional<TierlistLike> tierlistLikeOptional = tierlistLikeRepository
        .findByMemberIdAndTierlistId(member.getId(), tierlist.getId());
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlist.getId());

    assertThat(tierlistLikeOptional).isPresent();
    assertThat(tierlistLikeOptional.get().getMemberId()).isEqualTo(member.getId());
    assertThat(tierlistLikeOptional.get().getTierlistId()).isEqualTo(tierlist.getId());
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().getLikeCount()).isEqualTo(1);
  }

  @Test
  void 토픽_즐겨찾기를_삭제할_수_있다() {
    // given
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

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .topicId(topic.getId())
        .memberId(member.getId())
        .likeCount(1)
        .title("티어리스트")
        .build());

    tierlistLikeRepository.save(TierlistLike.builder()
        .memberId(member.getId())
        .tierlistId(tierlist.getId())
        .build());

    // when
    sut.toggleLike(member.getEmail(), tierlist.getId());

    // then
    Optional<TierlistLike> tierlistLikeOptional = tierlistLikeRepository
        .findByMemberIdAndTierlistId(member.getId(), tierlist.getId());
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlist.getId());

    assertThat(tierlistLikeOptional).isEmpty();
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().getLikeCount()).isZero();
  }

  @Test
  void 존재하지_않는_티어리스트에_좋아요를_토글_할_수_없다() {
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
      sut.toggleLike(memberEmail, 1L);
    }).isInstanceOf(TierlistNotFoundException.class);
  }
}