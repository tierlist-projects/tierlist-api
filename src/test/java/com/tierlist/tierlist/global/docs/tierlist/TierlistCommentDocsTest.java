package com.tierlist.tierlist.global.docs.tierlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistCommentController;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCommentCreateRequest;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistCommentNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistCommentController.class)
public class TierlistCommentDocsTest extends RestDocsTestSupport {

  @MockBean
  TierlistCommentCreateUseCase tierlistCommentCreateUseCase;

  @Test
  void create_tierlist_comment_201() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content("test content")
        .build();

    given(tierlistCommentCreateUseCase.createComment(any(), any(), any())).willReturn(2L);

    mvc.perform(post("/tierlist/{tierlistId}/comment", tierlistId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "/tierlist/1/comment/2"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            requestFields(
                fieldWithPath("parentCommentId")
                    .type(NUMBER)
                    .description("댓글이 생성될 상위 댓글 식별번호")
                    .optional()
                    .attributes(constraints("null이면 댓글 생성, null이 아니면 대댓글 생성")),
                fieldWithPath("content")
                    .type(STRING)
                    .description("댓글 내용")
            ),
            responseHeaders( //응답 헤더 문서화
                headerWithName(LOCATION)
                    .description("생성된 tierlist comment url")
            )
        ));
  }

  @Test
  void create_tierlist_comment_404_tierlist() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content("test content")
        .build();

    given(tierlistCommentCreateUseCase.createComment(any(), any(), any()))
        .willThrow(new TierlistNotFoundException());

    mvc.perform(post("/tierlist/{tierlistId}/comment", tierlistId)
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
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            requestFields(
                fieldWithPath("parentCommentId")
                    .type(NUMBER)
                    .description("댓글이 생성될 상위 댓글 식별번호")
                    .optional()
                    .attributes(constraints("null이면 댓글 생성, null이 아니면 대댓글 생성")),
                fieldWithPath("content")
                    .type(STRING)
                    .description("댓글 내용")
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
  void create_tierlist_comment_404_comment() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content("test content")
        .build();

    given(tierlistCommentCreateUseCase.createComment(any(), any(), any()))
        .willThrow(new TierlistCommentNotFoundException());

    mvc.perform(post("/tierlist/{tierlistId}/comment", tierlistId)
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
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            requestFields(
                fieldWithPath("parentCommentId")
                    .type(NUMBER)
                    .description("댓글이 생성될 상위 댓글 식별번호")
                    .optional()
                    .attributes(constraints("null이면 댓글 생성, null이 아니면 대댓글 생성")),
                fieldWithPath("content")
                    .type(STRING)
                    .description("댓글 내용")
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
  void create_tierlist_comment_403_not_published() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content("test content")
        .build();

    given(tierlistCommentCreateUseCase.createComment(any(), any(), any()))
        .willThrow(new TierlistAuthorizationException());

    mvc.perform(post("/tierlist/{tierlistId}/comment", tierlistId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isForbidden())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            requestFields(
                fieldWithPath("parentCommentId")
                    .type(NUMBER)
                    .description("댓글이 생성될 상위 댓글 식별번호")
                    .optional()
                    .attributes(constraints("null이면 댓글 생성, null이 아니면 대댓글 생성")),
                fieldWithPath("content")
                    .type(STRING)
                    .description("댓글 내용")
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
  void create_tierlist_comment_400_content_not_blank() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content(" ")
        .build();

    mvc.perform(post("/tierlist/{tierlistId}/comment", tierlistId)
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
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            requestFields(
                fieldWithPath("parentCommentId")
                    .type(NUMBER)
                    .description("댓글이 생성될 상위 댓글 식별번호")
                    .optional()
                    .attributes(constraints("null이면 댓글 생성, null이 아니면 대댓글 생성")),
                fieldWithPath("content")
                    .type(STRING)
                    .description("댓글 내용")
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
