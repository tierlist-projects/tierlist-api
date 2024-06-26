package com.tierlist.tierlist.global.docs.tierlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistCommentController;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCommentCreateRequest;
import com.tierlist.tierlist.tierlist.application.domain.exception.AddCommentOnChildException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistCommentNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentReadUseCase;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistCommentController.class)
class TierlistCommentDocsTest extends RestDocsTestSupport {

  @MockBean
  TierlistCommentCreateUseCase tierlistCommentCreateUseCase;

  @MockBean
  TierlistCommentReadUseCase tierlistCommentReadUseCase;

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
                fieldWithPath("commentId")
                    .description("생성된 코멘트 식별 번호")
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
  void create_tierlist_comment_400_add_comment_on_child() throws Exception {

    Long tierlistId = 1L;

    TierlistCommentCreateRequest request = TierlistCommentCreateRequest.builder()
        .parentCommentId(1L)
        .content("test content")
        .build();

    given(tierlistCommentCreateUseCase.createComment(any(), any(), any()))
        .willThrow(new AddCommentOnChildException());

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

  @Test
  void get_tierlist_comments_200() throws Exception {

    Long tierlistId = 1L;
    int page = 0;
    int size = 10;

    given(tierlistCommentReadUseCase.getTierlistComments(any(), any(), any()))
        .willReturn(
            PageResponse.<TierlistCommentResponse>builder()
                .numberOfElements(3)
                .pageNumber(0)
                .pageSize(3)
                .totalElements(3)
                .totalPages(1)
                .content(
                    List.of(
                        TierlistCommentResponse.builder()
                            .id(1L)
                            .writer(MemberResponse.builder()
                                .id(1L)
                                .nickname("nickname1")
                                .profileImage("profile-image-name-1")
                                .build())
                            .createdAt(LocalDateTime.of(2024, 4, 23, 12, 27, 40))
                            .content("댓글 내용 1")
                            .isMyComment(true)
                            .isParentComment(false)
                            .isTierlistWriter(true)
                            .build(),
                        TierlistCommentResponse.builder()
                            .id(3L)
                            .writer(MemberResponse.builder()
                                .id(1L)
                                .nickname("nickname1")
                                .profileImage("profile-image_name-1")
                                .build())
                            .createdAt(LocalDateTime.of(2024, 4, 23, 12, 29, 40))
                            .content("대댓글 내용 1")
                            .isMyComment(true)
                            .isParentComment(true)
                            .isTierlistWriter(false)
                            .build(),
                        TierlistCommentResponse.builder()
                            .id(2L)
                            .writer(MemberResponse.builder()
                                .id(2L)
                                .nickname("nickname2")
                                .profileImage("profile-image_name")
                                .build())
                            .createdAt(LocalDateTime.of(2024, 4, 23, 12, 28, 40))
                            .content("댓글 내용 2")
                            .isMyComment(true)
                            .isParentComment(true)
                            .isTierlistWriter(false)
                            .build()
                    )
                )
                .build()
        );

    mvc.perform(get("/tierlist/{tierlistId}/comment", tierlistId)
            .contentType(APPLICATION_JSON)
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("tierlistId").description("Tierlist ID")
            ),
            queryParameters(
                parameterWithName("page")
                    .description("페이지 넘버, default = 0")
                    .attributes(constraints("0부터 시작")),
                parameterWithName("size")
                    .description("페이지 당 컨텐츠 갯수, default = 20")
            ),
            responseFields(
                fieldWithPath("numberOfElements")
                    .description("컨텐츠의 갯수"),
                fieldWithPath("pageNumber")
                    .description("현재 페이지 번호(0부터 시작)"),
                fieldWithPath("pageSize")
                    .description("페이지당 컨텐츠 갯수"),
                fieldWithPath("totalPages")
                    .description("전체 페이지 갯수"),
                fieldWithPath("totalElements")
                    .description("컨텐츠 전체의 갯수"),
                fieldWithPath("content.[].id")
                    .description("댓글 식별번호"),
                fieldWithPath("content.[].writer")
                    .description("작성자 정보"),
                fieldWithPath("content.[].writer.id")
                    .description("작성자 식별번호"),
                fieldWithPath("content.[].writer.nickname")
                    .description("작성자 닉네임"),
                fieldWithPath("content.[].writer.profileImage")
                    .description("작성자 프로필 이미지 파일명"),
                fieldWithPath("content.[].content")
                    .description("댓글 내용"),
                fieldWithPath("content.[].createdAt")
                    .description("댓글 작성 시간"),
                fieldWithPath("content.[].myComment")
                    .description("해당 댓글이 작성자가 작성한 댓글인지 여부"),
                fieldWithPath("content.[].parentComment")
                    .description("해당 댓글이 댓글인지 대댓글인지 여부 (댓글이면 true)"),
                fieldWithPath("content.[].tierlistWriter")
                    .description("해당 댓글이 해당 티어리스트의 작성자가 생성했는지 여부")
            )
        ));
  }

  @Test
  void get_tierlist_comments_404() throws Exception {

    Long tierlistId = 1L;
    int page = 0;
    int size = 10;

    given(tierlistCommentReadUseCase.getTierlistComments(any(), any(), any()))
        .willThrow(new TierlistNotFoundException());

    mvc.perform(get("/tierlist/{tierlistId}/comment", tierlistId)
            .contentType(APPLICATION_JSON)
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
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
            queryParameters(
                parameterWithName("page")
                    .description("페이지 넘버, default = 0")
                    .attributes(constraints("0부터 시작")),
                parameterWithName("size")
                    .description("페이지 당 컨텐츠 갯수, default = 20")
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
