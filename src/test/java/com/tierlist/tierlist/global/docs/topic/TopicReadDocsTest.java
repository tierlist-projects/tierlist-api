package com.tierlist.tierlist.global.docs.topic;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.topic.adapter.in.web.TopicReadController;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

@WebMvcTest(TopicReadController.class)
class TopicReadDocsTest extends RestDocsTestSupport {

  @MockBean
  private TopicReadUseCase topicReadUseCase;

  @Test
  void read_topic_200() throws Exception {

    int page = 0;
    int size = 10;
    String query = "qqq";
    CategoryFilter filter = CategoryFilter.HOT;

    given(topicReadUseCase.getTopics(any(), any(), any(), any(), any())).willReturn(
        PageResponse.<TopicResponse>builder()
            .numberOfElements(2)
            .pageNumber(0)
            .pageSize(2)
            .totalElements(3)
            .totalPages(2)
            .content(
                List.of(
                    TopicResponse.builder().
                        id(1L)
                        .name("토픽1")
                        .favoriteCount(10)
                        .isFavorite(true)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .favoriteCount(10)
                                .name("카테고리1")
                                .isFavorite(false)
                                .build())
                        .build(),
                    TopicResponse.builder().
                        id(2L)
                        .name("토픽2")
                        .favoriteCount(10)
                        .isFavorite(false)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .favoriteCount(10)
                                .name("카테고리1")
                                .isFavorite(false)
                                .build())
                        .build()
                )
            )
            .build()
    );

    mvc.perform(get("/topic")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("query", query)
            .queryParam("filter", filter.name())
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            queryParameters(
                parameterWithName("page")
                    .description("페이지 넘버, default = 0")
                    .attributes(constraints("0부터 시작")),
                parameterWithName("size")
                    .description("페이지 당 컨텐츠 갯수, default = 20"),
                parameterWithName("query")
                    .description("검색어")
                    .optional()
                    .attributes(constraints("2글자 이상")),
                parameterWithName("filter")
                    .description("정렬 필터 HOT: 즐겨찾기 많은 순서 NONE: 이름 오름차순")
                    .attributes(constraints("HOT, NONE 중 하나여야 함."))
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
                fieldWithPath("content.[]")
                    .description("토픽 목록"),
                fieldWithPath("content.[].id")
                    .description("토픽 식별번호"),
                fieldWithPath("content.[].name")
                    .description("토픽 이름"),
                fieldWithPath("content.[].isFavorite")
                    .description("토픽 즐겨찾기 여부. 로그인 안했을 시 모두 false"),
                fieldWithPath("content.[].favoriteCount")
                    .description("토픽 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.id")
                    .description("토픽이 해당된 카테고리 식별 번호"),
                fieldWithPath("content.[].category.favoriteCount")
                    .description("토픽이 해당되는 카테고리 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.name")
                    .description("토픽이 해당된 카테고리 이름"),
                fieldWithPath("content.[].category.isFavorite")
                    .description("토픽이 해당된 카테고리 즐겨찾기 여부")
            )
        ));
  }

  @Test
  void read_topic_of_category_200() throws Exception {

    long categoryId = 1L;

    int page = 0;
    int size = 10;
    String query = "qqq";
    CategoryFilter filter = CategoryFilter.HOT;

    given(topicReadUseCase.getTopics(any(), any(), any(), any(), any())).willReturn(
        PageResponse.<TopicResponse>builder()
            .numberOfElements(2)
            .pageNumber(0)
            .pageSize(2)
            .totalElements(3)
            .totalPages(2)
            .content(
                List.of(
                    TopicResponse.builder().
                        id(1L)
                        .name("토픽1")
                        .favoriteCount(10)
                        .isFavorite(true)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .name("카테고리1")
                                .favoriteCount(10)
                                .isFavorite(false)
                                .build())
                        .build(),
                    TopicResponse.builder().
                        id(2L)
                        .name("토픽2")
                        .favoriteCount(10)
                        .isFavorite(false)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .name("카테고리1")
                                .favoriteCount(10)
                                .isFavorite(false)
                                .build())
                        .build()
                )
            ).build()
    );

    mvc.perform(RestDocumentationRequestBuilders.get("/category/{categoryId}/topic", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("query", query)
            .queryParam("filter", filter.name())
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            pathParameters(
                parameterWithName("categoryId")
                    .description("Category ID")
            ),
            queryParameters(
                parameterWithName("page")
                    .description("페이지 넘버, default = 0")
                    .attributes(constraints("0부터 시작")),
                parameterWithName("size")
                    .description("페이지 당 컨텐츠 갯수, default = 20"),
                parameterWithName("query")
                    .description("검색어")
                    .optional()
                    .attributes(constraints("2글자 이상")),
                parameterWithName("filter")
                    .description("정렬 필터 HOT: 즐겨찾기 많은 순서 NONE: 이름 오름차순")
                    .attributes(constraints("HOT, NONE 중 하나여야 함."))
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
                fieldWithPath("content.[]")
                    .description("토픽 목록"),
                fieldWithPath("content.[].id")
                    .description("토픽 식별번호"),
                fieldWithPath("content.[].name")
                    .description("토픽 이름"),
                fieldWithPath("content.[].isFavorite")
                    .description("토픽 즐겨찾기 여부. 로그인 안했을 시 모두 false"),
                fieldWithPath("content.[].favoriteCount")
                    .description("토픽 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.id")
                    .description("토픽이 해당된 카테고리 식별 번호"),
                fieldWithPath("content.[].category.favoriteCount")
                    .description("토픽이 해당되는 카테고리 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.name")
                    .description("토픽이 해당된 카테고리 이름"),
                fieldWithPath("content.[].category.isFavorite")
                    .description("토픽이 해당된 카테고리 즐겨찾기 여부")
            )
        ));
  }

  @Test
  void read_topic_of_category_404() throws Exception {

    long categoryId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    CategoryFilter filter = CategoryFilter.HOT;

    given(topicReadUseCase.getTopics(any(), any(), any(), any(), any()))
        .willThrow(new CategoryNotFoundException());

    mvc.perform(RestDocumentationRequestBuilders.get("/category/{categoryId}/topic", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("pageCount", String.valueOf(pageCount))
            .queryParam("pageSize", String.valueOf(pageSize))
            .queryParam("query", query)
            .queryParam("filter", filter.name())
        )
        .andExpect(status().isNotFound())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            pathParameters(
                parameterWithName("categoryId")
                    .description("Category ID")
            ),
            queryParameters(
                parameterWithName("pageCount")
                    .description("페이지 넘버")
                    .attributes(constraints("1부터 시작")),
                parameterWithName("pageSize")
                    .description("페이지 당 컨텐츠 갯수")
                    .attributes(constraints("1부터 시작")),
                parameterWithName("query")
                    .description("검색어")
                    .optional()
                    .attributes(constraints("2글자 이상")),
                parameterWithName("filter")
                    .description("정렬 필터 HOT: 즐겨찾기 많은 순서 NONE: 이름 오름차순")
                    .attributes(constraints("HOT, NONE 중 하나여야 함."))
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
  void read_favorite_topic_200() throws Exception {

    int page = 0;
    int size = 10;
    String query = "qqq";
    CategoryFilter filter = CategoryFilter.HOT;

    given(topicReadUseCase.getFavoriteTopics(any(), any())).willReturn(
        PageResponse.<TopicResponse>builder()
            .numberOfElements(2)
            .pageNumber(0)
            .pageSize(2)
            .totalElements(3)
            .totalPages(2)
            .content(
                List.of(
                    TopicResponse.builder().
                        id(1L)
                        .name("토픽1")
                        .favoriteCount(10)
                        .isFavorite(true)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .name("카테고리1")
                                .favoriteCount(10)
                                .isFavorite(false)
                                .build())
                        .build(),
                    TopicResponse.builder().
                        id(2L)
                        .name("토픽2")
                        .favoriteCount(10)
                        .isFavorite(false)
                        .category(
                            CategoryResponse.builder()
                                .id(1L)
                                .name("카테고리1")
                                .favoriteCount(10)
                                .isFavorite(false)
                                .build())
                        .build()
                )
            ).build()
    );

    mvc.perform(get("/topic/favorite")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
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
                fieldWithPath("content.[]")
                    .description("토픽 목록"),
                fieldWithPath("content.[].id")
                    .description("토픽 식별번호"),
                fieldWithPath("content.[].name")
                    .description("토픽 이름"),
                fieldWithPath("content.[].isFavorite")
                    .description("토픽 즐겨찾기 여부. 로그인 안했을 시 모두 false"),
                fieldWithPath("content.[].favoriteCount")
                    .description("토픽 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.id")
                    .description("토픽이 해당된 카테고리 식별 번호"),
                fieldWithPath("content.[].category.favoriteCount")
                    .description("토픽이 해당되는 카테고리 즐겨찾기 갯수"),
                fieldWithPath("content.[].category.name")
                    .description("토픽이 해당된 카테고리 이름"),
                fieldWithPath("content.[].category.isFavorite")
                    .description("토픽이 해당된 카테고리 즐겨찾기 여부")
            )
        ));
  }
}
