package com.tierlist.tierlist.global.docs.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.category.adaptor.in.web.CategoryFavoriteController;
import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.port.in.service.CategoryFavoriteUseCase;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(CategoryFavoriteController.class)
class CategoryFavoriteDocsTest extends RestDocsTestSupport {

  @MockBean
  private CategoryFavoriteUseCase categoryFavoriteUseCase;

  @Test
  void toggle_category_favorite_200() throws Exception {

    Long categoryId = 1L;

    mvc.perform(patch("/category/{categoryId}/favorite/toggle", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            pathParameters(
                parameterWithName("categoryId").description("Category ID")
            )
        ));
  }

  @Test
  void toggle_category_favorite_404() throws Exception {

    Long categoryId = 1L;

    doThrow(new CategoryNotFoundException())
        .when(categoryFavoriteUseCase).toggleFavorite(any(), any());

    mvc.perform(patch("/category/{categoryId}/favorite/toggle", categoryId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
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
