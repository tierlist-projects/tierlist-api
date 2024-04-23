package com.tierlist.tierlist.tierlist.application.port.in.service;

public interface TierlistLikeUseCase {

  void toggleLike(String email, Long tierlistId);

}
