package com.tierlist.tierlist.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tierlist.tierlist.global.jwt.entrypoint.JwtAuthenticationEntryPoint;
import com.tierlist.tierlist.global.jwt.property.JwtProperties;
import com.tierlist.tierlist.global.jwt.service.JwtService;
import com.tierlist.tierlist.global.security.filter.JsonLoginProcessingFilter;
import com.tierlist.tierlist.global.security.filter.JwtAuthenticationProcessingFilter;
import com.tierlist.tierlist.global.security.filter.TokenReissueFilter;
import com.tierlist.tierlist.global.security.handler.ErrorResponseAuthenticationFailureHandler;
import com.tierlist.tierlist.global.security.handler.JwtAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final CorsConfigurationSource corsConfigurationSource;
  private final ObjectMapper objectMapper;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtService jwtService;
  private final JwtProperties jwtProperties;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.cors(cors -> cors.configurationSource(corsConfigurationSource));
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/member/me/**").authenticated()
        .requestMatchers(HttpMethod.POST, "/image").authenticated()
        .requestMatchers(HttpMethod.POST, "/category").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/category/*/favorite/toggle").authenticated()
        .requestMatchers(HttpMethod.GET, "/category/favorite").authenticated()
        .anyRequest().permitAll());

    http.exceptionHandling(exceptionHandling ->
        exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()));

    http.addFilterAt(jsonLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(tokenReissueFilter(), JsonLoginProcessingFilter.class);
    http.addFilterBefore(jwtAuthenticationProcessingFilter(), TokenReissueFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new JwtAuthenticationEntryPoint(objectMapper);
  }

  @Bean
  public AbstractAuthenticationProcessingFilter jsonLoginProcessingFilter() throws Exception {
    JsonLoginProcessingFilter jsonLoginProcessingFilter =
        new JsonLoginProcessingFilter(objectMapper);

    jsonLoginProcessingFilter.setAuthenticationManager(authenticationManager());
    jsonLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
    jsonLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

    return jsonLoginProcessingFilter;
  }

  @Bean
  public TokenReissueFilter tokenReissueFilter() {
    return new TokenReissueFilter(jwtService, jwtProperties, objectMapper,
        authenticationEntryPoint());
  }

  @Bean
  public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
    return new JwtAuthenticationProcessingFilter(jwtService, jwtProperties);
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new JwtAuthenticationSuccessHandler(jwtService, objectMapper);
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new ErrorResponseAuthenticationFailureHandler(objectMapper);
  }


  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
