package com.tierlist.tierlist.global.docs.item;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.item.adapter.in.web.ItemReadController;
import com.tierlist.tierlist.item.application.port.in.service.ItemReadUseCase;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ItemReadController.class)
class ItemReadDocsTest extends RestDocsTestSupport {

  @MockBean
  private ItemReadUseCase itemReadUseCase;

  @Test
  void read_item_of_category_200() throws Exception {

    long categoryId = 1L;

    int page = 0;
    int size = 3;
    String query = "qqq";

    given(itemReadUseCase.getItems(any(), any(), any())).willReturn(
        PageResponse.<ItemResponse>builder()
            .numberOfElements(3)
            .pageNumber(0)
            .pageSize(3)
            .totalElements(4)
            .totalPages(2)
            .content(
                List.of(
                    ItemResponse.builder()
                        .id(1L)
                        .name("아이템1")
                        .build(),
                    ItemResponse.builder()
                        .id(2L)
                        .name("아이템2")
                        .build(),
                    ItemResponse.builder()
                        .id(3L)
                        .name("아이템3")
                        .build()
                )
            )
            .build()
    );

    mvc.perform(get("/category/{categoryId}/item", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("query", query)
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
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
                    .description("아이템 목록"),
                fieldWithPath("content.[].id")
                    .description("아이템 식별번호"),
                fieldWithPath("content.[].name")
                    .description("아이템 이름")
            )
        ));
  }

  @Test
  void read_item_of_category_404() throws Exception {

    long categoryId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";

    given(itemReadUseCase.getItems(any(), any(), any()))
        .willThrow(new CategoryNotFoundException());

    mvc.perform(get("/category/{categoryId}/item", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("pageCount", String.valueOf(pageCount))
            .queryParam("pageSize", String.valueOf(pageSize))
            .queryParam("query", query)
        )
        .andExpect(status().isNotFound())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
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
                    .attributes(constraints("2글자 이상"))
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
