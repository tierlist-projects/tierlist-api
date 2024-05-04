package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLoadRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TierlistReadService implements TierlistReadUseCase {

  private final MemberRepository memberRepository;
  private final TierlistRepository tierlistRepository;
  private final TierlistLoadRepository tierlistLoadRepository;

  @Override
  public TierlistDetailResponse getTierlist(String email, Long tierlistId) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    Tierlist tierlist = tierlistRepository.findById(tierlistId)
        .orElseThrow(TierlistNotFoundException::new);

    if (!tierlist.canView(member)) {
      throw new TierlistAuthorizationException();
    }
    return tierlistLoadRepository.loadTierlistById(email, tierlistId);
  }

  @Override
  public List<TierlistResponse> getTierlists(String email, int pageCount, int pageSize,
      String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getMyTierlists(String email, int pageCount, int pageSize,
      String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getTierlistsOfCategory(String email, Long categoryId, int pageCount,
      int pageSize, String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getTierlistsOfTopic(String email, Long topicId, int pageCount,
      int pageSize, String query, TierlistFilter filter) {
    return List.of();
  }
}
