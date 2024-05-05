package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.AddCommentOnChildException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistCommentNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCommandCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistCommentRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistCommentCreateService implements TierlistCommentCreateUseCase {

  private final MemberRepository memberRepository;
  private final TierlistRepository tierlistRepository;
  private final TierlistCommentRepository tierlistCommentRepository;

  private static void validateIsParentComment(TierlistComment parentComment) {
    if (!Objects.isNull(parentComment.getParentCommentId())) {
      throw new AddCommentOnChildException();
    }
  }

  @Transactional
  @Override
  public Long createComment(String email, Long tierlistId, TierlistCommandCreateCommand command) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    Tierlist tierlist = tierlistRepository.findById(tierlistId)
        .orElseThrow(TierlistNotFoundException::new);

    if (!tierlist.canAddComment()) {
      throw new TierlistAuthorizationException();
    }

    TierlistComment parentComment = findCommentIfExists(command.getParentCommentId());

    if (!Objects.isNull(parentComment)) {
      validateIsParentComment(parentComment);
    }

    TierlistComment tierlistComment = tierlistCommentRepository.save(
        command.toTierlistComment(tierlistId, member.getId()));

    tierlistComment.bindRootId();
    tierlistCommentRepository.save(tierlistComment);

    return tierlistComment.getId();
  }

  private TierlistComment findCommentIfExists(Long tierlistCommentId) {
    if (Objects.isNull(tierlistCommentId)) {
      return null;
    }

    return tierlistCommentRepository.findById(tierlistCommentId)
        .orElseThrow(TierlistCommentNotFoundException::new);
  }
}
