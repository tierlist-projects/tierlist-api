package com.tierlist.tierlist.global.docs.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.category.adaptor.in.web.CategoryReadController;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.CategoryReadUseCase;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(CategoryReadController.class)
class CategoryReadDocsTest extends RestDocsTestSupport {

  @MockBean
  private CategoryReadUseCase categoryReadUseCase;

  @Test
  void read_category_200() throws Exception {

    int pageCount = 1;
    int pageSize = 10;
    String query = "카테";
    CategoryFilter filter = CategoryFilter.HOT;

    given(categoryReadUseCase.getCategories(anyInt(), anyInt(), any(), any())).willReturn(
        List.of(
            CategoryResponse.builder()
                .id(1L)
                .name("카테고리1")
                .isFavorite(false)
                .build(),
            CategoryResponse.builder()
                .id(2L)
                .name("카테고리2")
                .isFavorite(true)
                .build(),
            CategoryResponse.builder()
                .id(3L)
                .name("카테고리3")
                .isFavorite(false)
                .build()
        )
    );

    mvc.perform(get("/category")
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
            .queryParam("pageCount", String.valueOf(pageCount))
            .queryParam("pageSize", String.valueOf(pageSize))
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
                parameterWithName("pageCount")
                    .description("페이지 넘버")
                    .attributes(constraints("1부터 시작")),
                parameterWithName("pageSize")
                    .description("페이지 사이즈")
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
                fieldWithPath("[]")
                    .description("카테고리 목록"),
                fieldWithPath("[].id")
                    .description("카테고리 식별번호"),
                fieldWithPath("[].name")
                    .description("카테고리 이름"),
                fieldWithPath("[].isFavorite")
                    .description("즐겨찾기 여부. 로그인 안했을 시 모두 false")
            )
        ));
  }
}
