package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistPublishUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistPublishService implements TierlistPublishUseCase {

  private final MemberRepository memberRepository;
  private final TierlistRepository tierlistRepository;

  @Transactional
  @Override
  public void togglePublish(String email, Long tierlistId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    Tierlist tierlist = tierlistRepository.findById(tierlistId)
        .orElseThrow(TierlistNotFoundException::new);

    if (!Objects.equals(tierlist.getMemberId(), member.getId())) {
      throw new TierlistAuthorizationException();
    }

    tierlist.togglePublish();
    tierlistRepository.save(tierlist);
  }
}
