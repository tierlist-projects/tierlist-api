package com.tierlist.tierlist.tierlist.application.domain.service;


import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistLikeUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistLikeService implements TierlistLikeUseCase {

  @Override
  public void toggleLike(String email, Long tierlistId) {

  }
}
