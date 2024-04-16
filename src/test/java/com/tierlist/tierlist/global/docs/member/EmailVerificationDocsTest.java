package com.tierlist.tierlist.global.docs.member;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.member.adapter.in.web.EmailVerificationController;
import com.tierlist.tierlist.member.adapter.in.web.dto.EmailVerificationConfirmRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.SendEmailVerificationRequest;
import com.tierlist.tierlist.member.application.port.in.service.EmailVerificationUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(EmailVerificationController.class)
public class EmailVerificationDocsTest extends RestDocsTestSupport {

  @MockBean
  private EmailVerificationUseCase emailVerificationUseCase;

  @Test
  public void request_email_verification_200() throws Exception {
    SendEmailVerificationRequest request = new SendEmailVerificationRequest("test@test.com");

    mvc.perform(post("/member/email/verification/request")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식"))
            )
        ));
  }

  @Test
  public void request_email_verification_400() throws Exception {
    SendEmailVerificationRequest request = new SendEmailVerificationRequest("testtestcom");

    mvc.perform(post("/member/email/verification/request")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("IR-004"))
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식"))
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

  @Test
  public void confirm_email_verification_200() throws Exception {
    EmailVerificationConfirmRequest request =
        new EmailVerificationConfirmRequest("test@test.com", "123456");

    BDDMockito.given(emailVerificationUseCase.verifyEmail(any())).willReturn(true);

    mvc.perform(post("/member/email/verification/confirm")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("code")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식"))
            )
        ));
  }

  @Test
  public void confirm_email_verification_404() throws Exception {
    EmailVerificationConfirmRequest request =
        new EmailVerificationConfirmRequest("test@test.com", "123456");

    BDDMockito.given(emailVerificationUseCase.verifyEmail(any())).willReturn(false);

    mvc.perform(post("/member/email/verification/confirm")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isNotFound())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("code")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식"))
            )
        ));
  }

  @Test
  public void confirm_email_verification_400() throws Exception {
    EmailVerificationConfirmRequest request =
        new EmailVerificationConfirmRequest("testtestcom", "1234567");

    mvc.perform(post("/member/email/verification/confirm")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식")),
                fieldWithPath("code")
                    .type(STRING)
                    .description("인증을 요청할 사용자 이메일")
                    .attributes(constraints("이메일 형식"))
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


