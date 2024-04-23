package com.tierlist.tierlist.global.docs.item;

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

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.item.adapter.in.web.ItemCreateController;
import com.tierlist.tierlist.item.adapter.in.web.dto.request.ItemCreateRequest;
import com.tierlist.tierlist.item.application.domain.exception.ItemNameDuplicationException;
import com.tierlist.tierlist.item.application.port.in.service.ItemCreateUseCase;
import com.tierlist.tierlist.topic.adapter.in.web.dto.request.TopicCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ItemCreateController.class)
class ItemCreateDocsTest extends RestDocsTestSupport {

  @MockBean
  private ItemCreateUseCase itemCreateUseCase;

  @Test
  void create_item_201() throws Exception {

    ItemCreateRequest request = ItemCreateRequest.builder()
        .categoryId(1L)
        .name("test")
        .build();

    given(itemCreateUseCase.create(any())).willReturn(1L);

    mvc.perform(post("/item")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "/item/1"))
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestFields(
                fieldWithPath("categoryId")
                    .type(NUMBER)
                    .description("아이템이 생성될 카테고리 식별번호"),
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 아이템 이름")
                    .attributes(constraints("아이템 이름은 2자 이상 10자 이하,"
                        + " 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
                        + "특수문자, 자음, 모음을 포함할 수 없습니다."))
            ),
            responseHeaders( //응답 헤더 문서화
                headerWithName(LOCATION)
                    .description("생성된 item url")
            )
        ));
  }

  @Test
  void create_item_400_invalid_input() throws Exception {

    ItemCreateRequest request = ItemCreateRequest.builder()
        .categoryId(1L)
        .name("t")
        .build();

    given(itemCreateUseCase.create(any())).willReturn(1L);

    mvc.perform(post("/item")
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
                fieldWithPath("categoryId")
                    .type(NUMBER)
                    .description("아이템이 생성될 카테고리 식별번호"),
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 아이템 이름")
                    .attributes(constraints("아이템 이름은 2자 이상 10자 이하,"
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

  @Test
  void create_item_404_category_not_exist() throws Exception {

    TopicCreateRequest request = TopicCreateRequest.builder()
        .categoryId(1L)
        .name("test")
        .build();

    given(itemCreateUseCase.create(any())).willThrow(new CategoryNotFoundException());

    mvc.perform(post("/topic")
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
                fieldWithPath("categoryId")
                    .type(NUMBER)
                    .description("아이템이 생성될 카테고리 식별번호"),
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 토픽 이름")
                    .attributes(constraints("토픽 이름은 2자 이상 20자 이하,"
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
  void create_item_409_item_name_duplication() throws Exception {

    ItemCreateRequest request = ItemCreateRequest.builder()
        .categoryId(1L)
        .name("test")
        .build();

    given(itemCreateUseCase.create(any())).willThrow(new ItemNameDuplicationException());

    mvc.perform(post("/item")
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
                fieldWithPath("categoryId")
                    .type(NUMBER)
                    .description("아이템이 생성될 카테고리 식별번호"),
                fieldWithPath("name")
                    .type(STRING)
                    .description("생성할 아이템 이름")
                    .attributes(constraints("아이템 이름은 2자 이상 10자 이하,"
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
}
