package com.tierlist.tierlist.tierlist.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TierlistCommentTest {

  @Test
  void 부모_댓글이_없는_경우_rootId_는_자기_자신이다() {
    TierlistComment tierlistComment = TierlistComment.builder()
        .parentCommentId(null)
        .build();

    assertThat(tierlistComment.getRootId()).isNull();
    assertThat(tierlistComment.getParentCommentId()).isNull();
  }

  @Test
  void 부모_댓글이_존재하는_경우_rootId_는_부모_댓글_이다() {
    TierlistComment tierlistComment = TierlistComment.builder()
        .parentCommentId(1L)
        .build();

    assertThat(tierlistComment.getRootId()).isOne();
    assertThat(tierlistComment.getParentCommentId()).isOne();
  }

}