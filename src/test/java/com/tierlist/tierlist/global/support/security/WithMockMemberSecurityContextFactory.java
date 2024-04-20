package com.tierlist.tierlist.global.support.security;

import java.util.Collections;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMemberSecurityContextFactory implements
    WithSecurityContextFactory<WithMockMember> {


  @Override
  public SecurityContext createSecurityContext(WithMockMember annotation) {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();

    List<SimpleGrantedAuthority> roles = Collections.emptyList();

    UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(
        annotation.email(), null, roles);

    context.setAuthentication(token);
    return context;
  }
}
