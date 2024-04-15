package com.tierlist.tierlist.global.docs.auth;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.global.jwt.property.JwtProperties;
import com.tierlist.tierlist.global.jwt.service.JwtService;
import com.tierlist.tierlist.global.security.config.PasswordEncoderConfig;
import com.tierlist.tierlist.global.security.config.SecurityConfig;
import com.tierlist.tierlist.global.security.dto.response.TokenResponse;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@WebMvcTest(
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
    }
)
@Import(
    {
        SecurityConfig.class,
        PasswordEncoderConfig.class,
    }
)
public class LoginDocsTest extends RestDocsTestSupport {

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private JwtProperties jwtProperties;

  @Test
  public void login_200() throws Exception {

    String email = "test@test.com";
    String password = "sample_password";

    Map<String, String> loginRequest
        = Map.of("email", email, "password", password);

    given(userDetailsService.loadUserByUsername(any())).willReturn(User.builder()
        .username(email)
        .password(passwordEncoder.encode(password))
        .build());

    given(jwtService.generateTokens(any(), any())).willReturn(new TokenResponse(
        "Bearer",
        "access.token.example",
        3600L,
        "refresh.token.example",
        36000L
    ));

    mvc.perform(
            post("/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andExpect(jsonPath("$.accessToken").value("access.token.example"))
        .andExpect(jsonPath("$.accessTokenExpiresIn").value("3600"))
        .andExpect(jsonPath("$.refreshToken").value("refresh.token.example"))
        .andExpect(jsonPath("$.refreshTokenExpiresIn").value("36000"))
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("password")
                    .type(STRING)
                    .description("사용자 패스워드")
                    .attributes(constraints("8자 이상 20자 이하의 문자열"))
            ),
            responseFields(
                fieldWithPath("tokenType")
                    .type(STRING)
                    .description("토큰 타입"),
                fieldWithPath("accessToken")
                    .type(STRING)
                    .description("JWT access token"),
                fieldWithPath("accessTokenExpiresIn")
                    .type(NUMBER)
                    .description("access token 유효 시간 (초단위)"),
                fieldWithPath("refreshToken")
                    .type(STRING)
                    .description("access token 재발급을 위한 토큰"),
                fieldWithPath("refreshTokenExpiresIn")
                    .type(NUMBER)
                    .description("refresh token 유효 시간 (초단위)")
            )
        ));
  }

  @Test
  public void login_401() throws Exception {

    String email = "test@test.com";
    String password = "sample_password";

    Map<String, String> loginRequest
        = Map.of("email", email, "password", password);

    given(userDetailsService.loadUserByUsername(any())).willThrow(UsernameNotFoundException.class);

    mvc.perform(
            post("/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        )
        .andExpect(status().isUnauthorized())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("password")
                    .type(STRING)
                    .description("사용자 패스워드")
                    .attributes(constraints("8자 이상 20자 이하의 문자열"))
            ),
            responseFields(
                fieldWithPath("errorCode")
                    .type(STRING)
                    .description("에러 코드"),
                fieldWithPath("message")
                    .type(STRING)
                    .description("에러 메세지")
            )
        ));
  }
}
