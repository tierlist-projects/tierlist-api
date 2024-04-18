package com.tierlist.tierlist.global.docs.auth;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tierlist.tierlist.global.docs.SpringSecurityRestDocsTestSupport;
import com.tierlist.tierlist.global.jwt.exception.UnexpectedRefreshTokenException;
import com.tierlist.tierlist.global.jwt.property.JwtProperties;
import com.tierlist.tierlist.global.jwt.service.JwtService;
import com.tierlist.tierlist.global.security.config.PasswordEncoderConfig;
import com.tierlist.tierlist.global.security.config.SecurityConfig;
import com.tierlist.tierlist.global.security.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        JwtProperties.class
    }
)
class ReissueDocsTest extends SpringSecurityRestDocsTestSupport {

  @MockBean
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @MockBean
  private JwtService jwtService;

  @Test
  public void reissue_200() throws Exception {

    String refreshToken = "Bearer refresh.token.example";

    given(jwtService.reissue(any())).willReturn(
        new TokenResponse(
            "Bearer",
            "access.token.example",
            3600L,
            "refresh.token.example",
            36000L
        )
    );

    mvc.perform(
            post("/reissue")
                .contentType(APPLICATION_JSON)
                .header("Refresh-Token", refreshToken)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andExpect(jsonPath("$.accessToken").value("access.token.example"))
        .andExpect(jsonPath("$.accessTokenExpiresIn").value("3600"))
        .andExpect(jsonPath("$.refreshToken").value("refresh.token.example"))
        .andExpect(jsonPath("$.refreshTokenExpiresIn").value("36000"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Refresh-Token")
                    .description("만료 전 refresh token")
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
  void reissue_401_expired() throws Exception {

    String refreshToken = "Bearer refresh.token.example";

    given(jwtService.reissue(any())).willThrow(TokenExpiredException.class);

    mvc.perform(
            post("/reissue")
                .contentType(APPLICATION_JSON)
                .header("Refresh-Token", refreshToken)
        )
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.errorCode").value("A-003"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Refresh-Token")
                    .description("만료 전 refresh token")
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

  @Test
  void reissue_401_unexpected_token() throws Exception {

    String refreshToken = "Bearer refresh.token.example";

    given(jwtService.reissue(any())).willThrow(UnexpectedRefreshTokenException.class);

    mvc.perform(
            post("/reissue")
                .contentType(APPLICATION_JSON)
                .header("Refresh-Token", refreshToken)
        )
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.errorCode").value("A-005"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Refresh-Token")
                    .description("만료 전 refresh token")
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

  @Test
  void reissue_401_invalid_token() throws Exception {

    String refreshToken = "Bearer refresh.token.example";

    given(jwtService.reissue(any())).willThrow(JWTVerificationException.class);

    mvc.perform(
            post("/reissue")
                .contentType(APPLICATION_JSON)
                .header("Refresh-Token", refreshToken)
        )
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.errorCode").value("A-004"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Refresh-Token")
                    .description("만료 전 refresh token")
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
