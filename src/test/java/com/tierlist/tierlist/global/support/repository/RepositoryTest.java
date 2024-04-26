package com.tierlist.tierlist.global.support.repository;

import com.tierlist.tierlist.global.jwt.repository.RefreshTokenRepository;
import com.tierlist.tierlist.global.querydsl.TestQuerydslConfig;
import com.tierlist.tierlist.member.adapter.out.persistence.VerifiedEmailRedisRepository;
import com.tierlist.tierlist.member.application.port.out.persistence.EmailVerificationCodeRepository;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestQuerydslConfig.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class),
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RefreshTokenRepository.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = EmailVerificationCodeRepository.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = VerifiedEmailRedisRepository.class)
    }
)
public @interface RepositoryTest {

}
