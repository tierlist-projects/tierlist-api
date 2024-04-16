package com.tierlist.tierlist.global.docs.member;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.member.adapter.in.web.MemberValidationController;
import com.tierlist.tierlist.member.application.domain.exception.EmailDuplicationException;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MemberValidationController.class)
public class MemberValidationDocsTest extends RestDocsTestSupport {


  @MockBean
  private MemberValidationUseCase memberValidationUseCase;

  @Test
  public void validate_email_duplication_200() throws Exception {

    String email = "test@test.com";

    mvc.perform(get("/member/email/unique")
            .param("email", email))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("email")
                    .description("unique함을 검증할 이메일")
            )
        ));
  }

  @Test
  public void validate_email_duplication_409() throws Exception {

    String email = "test@test.com";

    BDDMockito.doThrow(new EmailDuplicationException())
        .when(memberValidationUseCase).validateEmailDuplication(any());

    mvc.perform(get("/member/email/unique")
            .param("email", email))
        .andExpect(jsonPath("$.errorCode").value("D-004"))
        .andExpect(status().isConflict())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("email")
                    .description("unique함을 검증할 이메일")
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
  public void validate_email_duplication_400() throws Exception {

    String email = "testtestcom";

    mvc.perform(get("/member/email/unique")
            .param("email", email))
        .andExpect(jsonPath("$.errorCode").value("IR-004"))
        .andExpect(status().isBadRequest())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("email")
                    .description("unique함을 검증할 이메일")
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
  public void validate_nickname_duplication_200() throws Exception {

    String nickname = "test";

    mvc.perform(get("/member/nickname/unique")
            .param("nickname", nickname))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("nickname")
                    .description("unique함을 검증할 닉네임")
            )
        ));
  }

  @Test
  public void validate_nickname_duplication_409() throws Exception {

    String nickname = "test";

    BDDMockito.doThrow(new NicknameDuplicationException())
        .when(memberValidationUseCase).validateNicknameDuplication(any());

    mvc.perform(get("/member/nickname/unique")
            .param("nickname", nickname))
        .andExpect(jsonPath("$.errorCode").value("D-003"))
        .andExpect(status().isConflict())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("nickname")
                    .description("unique함을 검증할 닉네임")
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
  public void validate_nickname_duplication_400() throws Exception {

    String nickname = "t";

    mvc.perform(get("/member/nickname/unique")
            .param("nickname", nickname))
        .andExpect(jsonPath("$.errorCode").value("IR-004"))
        .andExpect(status().isBadRequest())
        .andDo(restDocs.document(
            queryParameters(
                parameterWithName("nickname")
                    .description("unique함을 검증할 닉네임")
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
