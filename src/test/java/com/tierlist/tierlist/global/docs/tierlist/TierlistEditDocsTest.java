package com.tierlist.tierlist.global.docs.tierlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistEditController;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.ItemRankDto;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistEditRequest;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistEditUseCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistEditController.class)
class TierlistEditDocsTest extends RestDocsTestSupport {

  @MockBean
  private TierlistEditUseCase tierlistEditUseCase;

  @Test
  void edit_tierlist_200() throws Exception {

    Long tierlistId = 1L;

    TierlistEditRequest request = TierlistEditRequest.builder()
        .title("변경될 제목")
        .content("내용")
        .sRanks(List.of(
            ItemRankDto.builder()
                .itemId(1L)
                .itemRankImage("item-rank-1-image")
                .build(),
            ItemRankDto.builder()
                .itemId(2L)
                .itemRankImage("item-rank-2-image")
                .build()
        ))
        .aRanks(List.of(
            ItemRankDto.builder()
                .itemId(3L)
                .itemRankImage("item-rank-3-image")
                .build()
        ))
        .bRanks(List.of(
            ItemRankDto.builder()
                .itemId(4L)
                .itemRankImage("item-rank-4-image")
                .build(),
            ItemRankDto.builder()
                .itemId(5L)
                .itemRankImage("item-rank-5-image")
                .build(),
            ItemRankDto.builder()
                .itemId(5L)
                .itemRankImage("item-rank-6-image")
                .build()
        ))
        .cRanks(List.of())
        .dRanks(List.of())
        .fRanks(List.of())
        .noneRanks(List.of(
            ItemRankDto.builder()
                .itemId(7L)
                .itemRankImage("item-rank-7-image")
                .build(),
            ItemRankDto.builder()
                .itemId(8L)
                .itemRankImage("item-rank-8-image")
                .build()
        ))
        .build();

    mvc.perform(put("/tierlist/{tierlistId}", tierlistId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
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
            requestFields(
                fieldWithPath("title")
                    .type(STRING)
                    .description("티어리스트 제목"),
                fieldWithPath("content")
                    .type(STRING)
                    .description("티어리스트에 작성할 내용"),
                fieldWithPath("sranks")
                    .type(ARRAY)
                    .description("S랭크에 해당될 아이템 목록"),
                fieldWithPath("aranks")
                    .type(ARRAY)
                    .description("A랭크에 해당될 아이템 목록"),
                fieldWithPath("branks")
                    .type(ARRAY)
                    .description("B랭크에 해당될 아이템 목록"),
                fieldWithPath("cranks")
                    .type(ARRAY)
                    .description("C랭크에 해당될 아이템 목록"),
                fieldWithPath("dranks")
                    .type(ARRAY)
                    .description("D랭크에 해당될 아이템 목록"),
                fieldWithPath("franks")
                    .type(ARRAY)
                    .description("F랭크에 해당될 아이템 목록"),
                fieldWithPath("noneRanks")
                    .type(ARRAY)
                    .description("랭크에 해당되지 않는 아이템 목록"),
                fieldWithPath("*[].itemId")
                    .type(NUMBER)
                    .description("각 랭크에 해당될 아이템 식별번호"),
                fieldWithPath(
                    "*[].itemRankImage")
                    .type(STRING)
                    .description("각 랭크에 해당될 아이템 이미지")
            )
        ));
  }

  @Test
  void edit_tierlist_404() throws Exception {

    Long tierlistId = 1L;

    TierlistEditRequest request = TierlistEditRequest.builder()
        .title("변경될 제목")
        .content("내용")
        .sRanks(List.of(
            ItemRankDto.builder()
                .itemId(1L)
                .itemRankImage("item-rank-1-image")
                .build(),
            ItemRankDto.builder()
                .itemId(2L)
                .itemRankImage("item-rank-2-image")
                .build()
        ))
        .aRanks(List.of(
            ItemRankDto.builder()
                .itemId(3L)
                .itemRankImage("item-rank-3-image")
                .build()
        ))
        .bRanks(List.of(
            ItemRankDto.builder()
                .itemId(4L)
                .itemRankImage("item-rank-4-image")
                .build(),
            ItemRankDto.builder()
                .itemId(5L)
                .itemRankImage("item-rank-5-image")
                .build(),
            ItemRankDto.builder()
                .itemId(5L)
                .itemRankImage("item-rank-6-image")
                .build()
        ))
        .cRanks(List.of())
        .dRanks(List.of())
        .fRanks(List.of())
        .noneRanks(List.of(
            ItemRankDto.builder()
                .itemId(7L)
                .itemRankImage("item-rank-7-image")
                .build(),
            ItemRankDto.builder()
                .itemId(8L)
                .itemRankImage("item-rank-8-image")
                .build()
        ))
        .build();

    doThrow(new TierlistNotFoundException())
        .when(tierlistEditUseCase).editTierlist(any(), any(), any());

    mvc.perform(put("/tierlist/{tierlistId}", tierlistId)
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
  void edit_tierlist_400() throws Exception {

    Long tierlistId = 1L;

    TierlistEditRequest request = TierlistEditRequest.builder()
        .title(" ")
        .content(" ")
        .build();

    mvc.perform(put("/tierlist/{tierlistId}", tierlistId)
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
