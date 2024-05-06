package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLoadRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class TierlistReadServiceTest {

  @Mock
  private TierlistLoadRepository tierlistLoadRepository;

  private MemberRepository memberRepository;
  private TierlistRepository tierlistRepository;
  private TopicRepository topicRepository;
  private CategoryRepository categoryRepository;
  ;

  private PasswordEncoder passwordEncoder;

  private TierlistReadService tierlistReadService;

  @BeforeEach
  void init() {
    memberRepository = new FakeMemberRepository();
    tierlistRepository = new FakeTierlistRepository();
    topicRepository = new FakeTopicRepository();
    categoryRepository = new FakeCategoryRepository();

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    tierlistReadService = new TierlistReadService(memberRepository, tierlistRepository,
        tierlistLoadRepository, topicRepository, categoryRepository);
  }

  @Test
  void 존재하지_않는_티어리스트를_조회할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    // when
    // then
    String memberEmail = member.getEmail();
    assertThatThrownBy(() -> {
      tierlistReadService.getTierlist(memberEmail, 1L);
    }).isInstanceOf(TierlistNotFoundException.class);
  }

  @Test
  void 발행_상태가_아닌_티어리스트를_작성자가_아닌_경우_조회할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .title("티어리스트")
        .memberId(2L)
        .isPublished(false)
        .build());

    // when
    // then
    String memberEmail = member.getEmail();
    Long tierlistId = tierlist.getId();
    assertThatThrownBy(() -> {
      tierlistReadService.getTierlist(memberEmail, tierlistId);
    }).isInstanceOf(TierlistAuthorizationException.class);
  }

  @Test
  void 티어리스트_작성자는_티어리스트_발행_상태와_상관_없이_조회할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .title("티어리스트")
        .memberId(member.getId())
        .isPublished(false)
        .build());

    // when
    // then
    String memberEmail = member.getEmail();
    Long tierlistId = tierlist.getId();
    assertThatCode(() -> {
      tierlistReadService.getTierlist(memberEmail, tierlistId);
    }).doesNotThrowAnyException();
  }
}