package com.tierlist.tierlist.global.docs.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.global.support.security.WithMockMember;
import com.tierlist.tierlist.member.adapter.in.web.MemberInformationController;
import com.tierlist.tierlist.member.adapter.in.web.dto.request.ChangeMemberNicknameRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.port.in.service.MemberInformationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MemberInformationController.class)
class MemberInformationDocsTest extends RestDocsTestSupport {

  @MockBean
  private MemberInformationUseCase memberInformationUseCase;

  @WithMockMember
  @Test
  void get_own_information_200() throws Exception {

    MemberResponse memberResponse = MemberResponse.builder()
        .email("test@test.com")
        .nickname("TestNick")
        .profileImage("1234-5678-9012")
        .build();

    given(memberInformationUseCase.getMemberInformation(any())).willReturn(memberResponse);

    mvc.perform(get("/member/me")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            responseFields(
                fieldWithPath("email")
                    .type(STRING)
                    .description("사용자 이메일"),
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("사용자 닉네임"),
                fieldWithPath("profileImage")
                    .type(STRING)
                    .description("사용자 프로필 이미지")
            )
        ));
  }

  @WithMockMember
  @Test
  void change_member_nickname_200() throws Exception {

    ChangeMemberNicknameRequest request = new ChangeMemberNicknameRequest("NewNick");

    mvc.perform(patch("/member/me/nickname")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("변경할 닉네임")
            )
        ));
  }

  @WithMockMember
  @Test
  void change_member_nickname_409() throws Exception {

    ChangeMemberNicknameRequest request = new ChangeMemberNicknameRequest("NewNick");

    doThrow(new NicknameDuplicationException())
        .when(memberInformationUseCase).changeMemberNickname(any(), any());

    mvc.perform(patch("/member/me/nickname")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isConflict())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("변경할 닉네임")
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

  @WithMockMember
  @Test
  void change_member_nickname_400() throws Exception {

    ChangeMemberNicknameRequest request = new ChangeMemberNicknameRequest("1");

    doThrow(new NicknameDuplicationException())
        .when(memberInformationUseCase).changeMemberNickname(any(), any());

    mvc.perform(patch("/member/me/nickname")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("nickname")
                    .type(STRING)
                    .description("변경할 닉네임")
                    .attributes(constraints(
                        "닉네임은 10자 이하, 영어, 숫자 또는 한글로 구성되어야 하고,공백, 특수문자, 자음, 모음을 포함할 수 없습니다."))
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
