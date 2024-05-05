package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistCommentRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.AddCommentOnChildException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCommandCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistCommentRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TierlistCommentCreateServiceTest {

  private MemberRepository memberRepository;
  private TierlistRepository tierlistRepository;
  private TierlistCommentRepository tierlistCommentRepository;

  private PasswordEncoder passwordEncoder;

  private TierlistCommentCreateService sut;

  @BeforeEach
  void init() {
    memberRepository = new FakeMemberRepository();
    tierlistRepository = new FakeTierlistRepository();
    tierlistCommentRepository = new FakeTierlistCommentRepository();

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    sut = new TierlistCommentCreateService(memberRepository,
        tierlistRepository,
        tierlistCommentRepository);
  }

  @Test
  void 댓글을_생성할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .isPublished(true)
        .build());

    // when
    TierlistCommandCreateCommand command = TierlistCommandCreateCommand.builder()
        .content("댓글 내용")
        .parentCommentId(null)
        .build();

    Long commentId = sut.createComment(member.getEmail(), tierlist.getId(), command);

    // then
    Optional<TierlistComment> commentOptional = tierlistCommentRepository.findById(commentId);
    assertThat(commentOptional).isPresent();
    assertThat(commentOptional.get().getParentCommentId()).isNull();
    assertThat(commentOptional.get().getRootId()).isEqualTo(commentId);
    assertThat(commentOptional.get().getContent()).isEqualTo("댓글 내용");
  }

  @Test
  void 자식_댓글을_생성할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .isPublished(true)
        .build());

    TierlistComment parentComment = tierlistCommentRepository.save(TierlistComment.builder()
        .content("부모댓글")
        .tierlistId(tierlist.getId())
        .writerId(member.getId())
        .parentCommentId(null)
        .build());

    // when
    TierlistCommandCreateCommand command = TierlistCommandCreateCommand.builder()
        .content("댓글 내용")
        .parentCommentId(parentComment.getId())
        .build();

    Long commentId = sut.createComment(member.getEmail(), tierlist.getId(), command);

    // then
    Optional<TierlistComment> commentOptional = tierlistCommentRepository.findById(commentId);
    assertThat(commentOptional).isPresent();
    assertThat(commentOptional.get().getParentCommentId()).isEqualTo(parentComment.getId());
    assertThat(commentOptional.get().getRootId()).isEqualTo(parentComment.getId());
    assertThat(commentOptional.get().getContent()).isEqualTo("댓글 내용");
  }


  @Test
  void 발행_상태가_false_인_티어리스트에_댓글을_생성할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .isPublished(false)
        .build());

    // when
    // then
    TierlistCommandCreateCommand command = TierlistCommandCreateCommand.builder()
        .content("댓글 내용")
        .parentCommentId(null)
        .build();

    String memberEmail = member.getEmail();
    Long tierlistId = tierlist.getId();
    assertThatThrownBy(() -> {
      sut.createComment(memberEmail, tierlistId, command);
    }).isInstanceOf(TierlistAuthorizationException.class);
  }

  @Test
  void 자식_댓글에_댓글을_추가할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .isPublished(true)
        .build());

    TierlistComment parentComment = tierlistCommentRepository.save(TierlistComment.builder()
        .content("부모댓글")
        .tierlistId(tierlist.getId())
        .writerId(member.getId())
        .parentCommentId(null)
        .build());

    TierlistComment childComment = tierlistCommentRepository.save(TierlistComment.builder()
        .content("자식댓글")
        .tierlistId(tierlist.getId())
        .writerId(member.getId())
        .parentCommentId(parentComment.getId())
        .build());

    // when
    // then
    TierlistCommandCreateCommand command = TierlistCommandCreateCommand.builder()
        .content("댓글 내용")
        .parentCommentId(childComment.getId())
        .build();

    String memberEmail = member.getEmail();
    Long tierlistId = tierlist.getId();
    assertThatThrownBy(() -> {
      sut.createComment(memberEmail, tierlistId, command);
    }).isInstanceOf(AddCommentOnChildException.class);
  }

  @Test
  void 존재하지_않는_티어리스트에_댓글을_달_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Long tierlistId = 1L;

    // when
    TierlistCommandCreateCommand command = TierlistCommandCreateCommand.builder()
        .content("댓글 내용")
        .build();

    String memberEmail = member.getEmail();
    assertThatThrownBy(() -> {
      sut.createComment(memberEmail, tierlistId, command);
    }).isInstanceOf(TierlistNotFoundException.class);
  }

}