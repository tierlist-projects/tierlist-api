package com.tierlist.tierlist.member.adapter.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.port.out.persistence.VerifiedEmailRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VerifiedEmailRedisRepository implements VerifiedEmailRepository {

  private static final String KEY_PREFIX = "validatedEmail:";
  private final StringRedisTemplate redisTemplate;

  @Override
  public void save(String email, EmailVerificationCode code, Duration expireDuration) {
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    ops.set(KEY_PREFIX + email, code.getCode());
  }

  @Override
  public EmailVerificationCode get(String email) {
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    return EmailVerificationCode.of(ops.get(KEY_PREFIX + email));
  }

  @Override
  public void remove(String email) {
    redisTemplate.delete(KEY_PREFIX + email);
  }

  @Override
  public boolean has(String email) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(KEY_PREFIX + email));
  }

}
