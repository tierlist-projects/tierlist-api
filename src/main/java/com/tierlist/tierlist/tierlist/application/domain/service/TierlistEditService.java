package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistEditUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.ItemRankRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistEditService implements TierlistEditUseCase {

  private final MemberRepository memberRepository;
  private final TierlistRepository tierlistRepository;
  private final ItemRankRepository itemRankRepository;

  @Transactional
  @Override
  public void editTierlist(String email, Long tierlistId, TierlistEditCommand command) {
    Tierlist tierlist = tierlistRepository.findById(tierlistId)
        .orElseThrow(TierlistNotFoundException::new);

    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    if (!Objects.equals(tierlist.getMemberId(), member.getId())) {
      throw new TierlistAuthorizationException();
    }

    itemRankRepository.deleteAllByTierlistId(tierlistId);
    itemRankRepository.saveAll(command.toItemRanks(tierlistId));

    tierlist.edit(command);
    tierlistRepository.save(tierlist);
  }
}
