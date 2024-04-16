package com.tierlist.tierlist.global.docs.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.member.adapter.in.web.MemberSignupController;
import com.tierlist.tierlist.member.adapter.in.web.dto.MemberSignupRequest;
import com.tierlist.tierlist.member.application.domain.exception.InvalidEmailVerificationCodeException;
import com.tierlist.tierlist.member.application.port.in.service.MemberSignupUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MemberSignupController.class)
public class MemberSignupDocsTest extends RestDocsTestSupport {

  @MockBean
  private MemberSignupUseCase memberSignupUseCase;

  @Test
  public void signup_201() throws Exception {

    MemberSignupRequest request = MemberSignupRequest.builder()
        .email("test@test.com")
        .nickname("test")
        .password("test_password")
        .code("000000")
        .build();

    given(memberSignupUseCase.signup(any())).willReturn(1L);

    mvc.perform(post("/member")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "/member/1"))
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("사용자 닉네임")
                    .attributes(constraints("2자 이상 10자 이하의 영문, 한글, 숫자")),
                fieldWithPath("password")
                    .type(STRING)
                    .description("사용자 패스워드")
                    .attributes(constraints(
                        "password는 8자 이상, 20자 이상이어야 하고,영문 대문자, 소문자, 숫자, 특수문자 ! _ @ $ % ^ & + = 만 허용합니다")),
                fieldWithPath("code")
                    .type(STRING)
                    .description("이메일 인증 코드")
                    .attributes(constraints("000000 ~ 999999의 6자리 숫자"))
            ),
            responseHeaders( //응답 헤더 문서화
                headerWithName(LOCATION)
                    .description("생성된 member url")
            )
        ));
  }

  @Test
  public void signup_400_invalid_verification_code() throws Exception {

    MemberSignupRequest request = MemberSignupRequest.builder()
        .email("test@test.com")
        .nickname("test")
        .password("test_password")
        .code("000000")
        .build();

    given(memberSignupUseCase.signup(any())).willThrow(new InvalidEmailVerificationCodeException());

    mvc.perform(post("/member")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("IR-002"))
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일"),
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("사용자 닉네임"),
                fieldWithPath("password")
                    .type(STRING)
                    .description("사용자 패스워드"),
                fieldWithPath("code")
                    .type(STRING)
                    .description("이메일 인증 코드")
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
  public void signup_400_invalid_request_value() throws Exception {

    MemberSignupRequest request = MemberSignupRequest.builder()
        .email("testtestcom")
        .nickname("t")
        .password("test")
        .code("1234567")
        .build();

    mvc.perform(post("/member")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("IR-004"))
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일"),
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("사용자 닉네임"),
                fieldWithPath("password")
                    .type(STRING)
                    .description("사용자 패스워드"),
                fieldWithPath("code")
                    .type(STRING)
                    .description("이메일 인증 코드")
            ),
            responseFields(
                fieldWithPath("errorCode")
                    .type(STRING)
                    .description("에러 코드"),
                fieldWithPath("message")
                    .type(STRING)
                    .description("에러 메세지"),
                fieldWithPath("reasons")
                    .type(ARRAY)
                    .description("에러 원인")
            )
        ));
  }
}
