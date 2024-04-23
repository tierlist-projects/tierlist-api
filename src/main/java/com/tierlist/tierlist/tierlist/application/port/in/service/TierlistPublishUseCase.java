package com.tierlist.tierlist.tierlist.application.port.in.service;

public interface TierlistPublishUseCase {

  void togglePublish(String email, Long tierlistId);

}
