package com.tierlist.tierlist.global.jwt.repository;

import java.util.Optional;

public interface RefreshTokenRepository {

  void update(String username, String refreshToken);

  Optional<String> find(String username);

  void delete(String username);
}
