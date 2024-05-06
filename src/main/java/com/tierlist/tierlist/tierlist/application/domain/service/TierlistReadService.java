package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public PageResponse<TierlistResponse> getTierlists(String email, Pageable pageable, String query,
      TierlistFilter filter) {
    Page<TierlistResponse> tierlistResponses = tierlistLoadRepository.loadTierlists(email, pageable,
        query, filter);
    return PageResponse.fromPage(tierlistResponses);
  }

  @Override
  public PageResponse<TierlistResponse> getMyTierlists(String email, Pageable pageable,
      String query, TierlistFilter filter) {
    return null;
  }

  @Override
  public PageResponse<TierlistResponse> getTierlistsOfCategory(String email, Long categoryId,
      Pageable pageable, String query, TierlistFilter filter) {
    return null;
  }

  @Override
  public PageResponse<TierlistResponse> getTierlistsOfTopic(String email, Long topicId,
      Pageable pageable, String query, TierlistFilter filter) {
    return null;
  }

}
