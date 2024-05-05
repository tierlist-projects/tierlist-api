package com.tierlist.tierlist.tierlist.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TierlistCommentTest {

  @Test
  void 식별번호가_있는_경우에_rootId를_자신의_식별번호로_바인딩할_수_있다() {
    TierlistComment tierlistComment = TierlistComment.builder()
        .id(1L)
        .parentCommentId(null)
        .build();

    tierlistComment.bindRootId();

    assertThat(tierlistComment.getRootId()).isEqualTo(tierlistComment.getId());
    assertThat(tierlistComment.getParentCommentId()).isNull();
  }

  @Test
  void 식별번호가_없는_경우에_rootId를_바인딩할_수_없다() {
    TierlistComment tierlistComment = TierlistComment.builder()
        .parentCommentId(null)
        .build();

    assertThatThrownBy(tierlistComment::bindRootId).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void 부모_댓글이_존재하는_경우_바인딩된_rootId_는_부모_댓글_이다() {
    TierlistComment tierlistComment = TierlistComment.builder()
        .id(3L)
        .parentCommentId(1L)
        .build();

    tierlistComment.bindRootId();

    assertThat(tierlistComment.getRootId()).isEqualTo(tierlistComment.getParentCommentId());
  }

}