package com.tierlist.tierlist.global.docs.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.category.adapter.in.web.CategoryCreateController;
import com.tierlist.tierlist.category.adapter.in.web.dto.request.CreateCategoryRequest;
import com.tierlist.tierlist.category.application.domain.exception.CategoryNameDuplicationException;
import com.tierlist.tierlist.category.application.port.in.service.CategoryCreateUseCase;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(CategoryCreateController.class)
class CategoryCreateDocsTest extends RestDocsTestSupport {

  @MockBean
  CategoryCreateUseCase categoryCreateUseCase;

  @Test
  void create_category_201() throws Exception {

    CreateCategoryRequest request = CreateCategoryRequest.builder()
        .name("test")
        .build();

    given(categoryCreateUseCase.create(any())).willReturn(1L);

    mvc.perform(post("/category")
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
            requestFields(
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 카테고리 이름")
                    .attributes(constraints("카테고리 이름은 2자 이상 20자 이하,"
                        + " 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
                        + "특수문자, 자음, 모음을 포함할 수 없습니다."))
            ),
            responseFields(
                fieldWithPath("categoryId")
                    .description("생성된 카테고리 식별 번호")
            )
        ));
  }

  @Test
  void create_category_409() throws Exception {

    CreateCategoryRequest request = CreateCategoryRequest.builder()
        .name("test")
        .build();

    given(categoryCreateUseCase.create(any())).willThrow(new CategoryNameDuplicationException());

    mvc.perform(post("/category")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isConflict())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 카테고리 이름")
                    .attributes(constraints("카테고리 이름은 2자 이상 20자 이하,"
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
  void create_category_400() throws Exception {

    CreateCategoryRequest request = CreateCategoryRequest.builder()
        .name("t")
        .build();

    mvc.perform(post("/category")
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
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 카테고리 이름")
                    .attributes(constraints("카테고리 이름은 2자 이상 20자 이하,"
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
