package com.tierlist.tierlist.tierlist.application.domain.service;


import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistLikeUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLikeRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistLikeService implements TierlistLikeUseCase {

  private final MemberRepository memberRepository;
  private final TierlistRepository tierlistRepository;
  private final TierlistLikeRepository tierlistLikeRepository;

  @Transactional
  @Override
  public void toggleLike(String email, Long tierlistId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    Tierlist tierlist = tierlistRepository.findById(tierlistId)
        .orElseThrow(TierlistNotFoundException::new);

    Optional<TierlistLike> tierlistLikeOptional = tierlistLikeRepository.findByMemberIdAndTierlistId(
        member.getId(), tierlist.getId());

    if (tierlistLikeOptional.isEmpty()) {
      tierlistLikeRepository.save(TierlistLike.builder()
          .memberId(member.getId())
          .tierlistId(tierlist.getId())
          .build());
      tierlist.addLike();
      tierlistRepository.save(tierlist);
      return;
    }

    tierlistLikeRepository.delete(tierlistLikeOptional.get());
    tierlist.removeLike();
    tierlistRepository.save(tierlist);
  }
}
