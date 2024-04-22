package com.tierlist.tierlist.global.docs.tierlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistCreateController;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCreateRequest;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCreateUseCase;
import com.tierlist.tierlist.topic.application.exception.TopicNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistCreateController.class)
class TierlistCreateDocsTest extends RestDocsTestSupport {

  @MockBean
  private TierlistCreateUseCase tierlistCreateUseCase;

  @Test
  void create_tierlist_201() throws Exception {

    TierlistCreateRequest request = TierlistCreateRequest.builder()
        .topicId(1L)
        .title("test")
        .build();

    given(tierlistCreateUseCase.create(any(), any())).willReturn(1L);

    mvc.perform(post("/tierlist")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "/tierlist/1"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("topicId")
                    .type(NUMBER)
                    .description("티어리스트가 생성될 토픽 식별번호"),
                fieldWithPath("title")
                    .type(STRING)
                    .description("생성할 티어리스트 제목")
                    .attributes(constraints("티어리스트 제목은 2자 이상 25자 이하,"
                        + " 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
                        + "특수문자, 자음, 모음을 포함할 수 없습니다."))
            ),
            responseHeaders( //응답 헤더 문서화
                headerWithName(LOCATION)
                    .description("생성된 tierlist url")
            )
        ));
  }

  @Test
  void create_tierlist_404() throws Exception {

    TierlistCreateRequest request = TierlistCreateRequest.builder()
        .topicId(1L)
        .title("test")
        .build();

    given(tierlistCreateUseCase.create(any(), any())).willThrow(new TopicNotFoundException());

    mvc.perform(post("/tierlist")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isNotFound())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("topicId")
                    .type(NUMBER)
                    .description("티어리스트가 생성될 토픽 식별번호"),
                fieldWithPath("title")
                    .type(STRING)
                    .description("생성할 티어리스트 제목")
                    .attributes(constraints("티어리스트 제목은 2자 이상 25자 이하,"
                        + " 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
                        + "특수문자, 자음, 모음을 포함할 수 없습니다."))
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
  void create_tierlist_400() throws Exception {

    TierlistCreateRequest request = TierlistCreateRequest.builder()
        .topicId(1L)
        .title("t")
        .build();

    given(tierlistCreateUseCase.create(any(), any())).willReturn(1L);

    mvc.perform(post("/tierlist")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isBadRequest())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("topicId")
                    .type(NUMBER)
                    .description("티어리스트가 생성될 토픽 식별번호"),
                fieldWithPath("title")
                    .type(STRING)
                    .description("생성할 티어리스트 제목")
                    .attributes(constraints("티어리스트 제목은 2자 이상 25자 이하,"
                        + " 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
                        + "특수문자, 자음, 모음을 포함할 수 없습니다."))
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
