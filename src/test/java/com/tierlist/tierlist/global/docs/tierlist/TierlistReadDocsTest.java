package com.tierlist.tierlist.global.docs.tierlist;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistReadController;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRankResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistReadController.class)
public class TierlistReadDocsTest extends RestDocsTestSupport {

  @MockBean
  TierlistReadUseCase tierlistReadUseCase;

  @Test
  void read_tierlist_detail_200() throws Exception {

    Long tierlistId = 1L;

    given(tierlistReadUseCase.getTierlist(any(), any())).willReturn(
        TierlistDetailResponse.builder()
            .id(1L)
            .title("티어리스트 제목")
            .content("티어리스트 콘텐츠")
            .createdAt(LocalDateTime.of(2024, 4, 23, 19, 7, 16))
            .sRanks(
                List.of(
                    ItemRankResponse.builder()
                        .id(1L)
                        .name("아이템1")
                        .itemRankImage("item-rank-image-1")
                        .build(),
                    ItemRankResponse.builder()
                        .id(2L)
                        .name("아이템2")
                        .itemRankImage("item-rank-image-2")
                        .build()
                )
            )
            .aRanks(List.of())
            .bRanks(List.of())
            .cRanks(
                List.of(
                    ItemRankResponse.builder()
                        .id(3L)
                        .name("아이템3")
                        .itemRankImage("item-rank-image-3")
                        .build(),
                    ItemRankResponse.builder()
                        .id(4L)
                        .name("아이템4")
                        .itemRankImage("item-rank-image-4")
                        .build()
                )
            )
            .dRanks(List.of())
            .fRanks(List.of())
            .noneRanks(
                List.of(
                    ItemRankResponse.builder()
                        .id(5L)
                        .name("아이템5")
                        .itemRankImage("item-rank-image-5")
                        .build()
                ))
            .build()
    );

    mvc.perform(get("/tierlist/{tierlistId}", tierlistId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            pathParameters(
                parameterWithName("tierlistId")
                    .description("Tierlist ID")
            ),
            responseFields(
                fieldWithPath("id")
                    .description("티어리스트 식별번호"),
                fieldWithPath("title")
                    .description("티어리스트 제목"),
                fieldWithPath("content")
                    .description("티어리스트 내용"),
                fieldWithPath("createdAt")
                    .description("티어리스트 생성 시간"),
                fieldWithPath("sranks")
                    .description("S랭크에 해당된 아이템 목록"),
                fieldWithPath("aranks")
                    .description("A랭크에 해당된 아이템 목록"),
                fieldWithPath("branks")
                    .description("B랭크에 해당된 아이템 목록"),
                fieldWithPath("cranks")
                    .description("C랭크에 해당된 아이템 목록"),
                fieldWithPath("dranks")
                    .description("D랭크에 해당된 아이템 목록"),
                fieldWithPath("franks")
                    .description("F랭크에 해당된 아이템 목록"),
                fieldWithPath("noneRanks")
                    .description("랭크에 해당되지 않은 아이템 목록"),
                fieldWithPath("*[].id")
                    .description("아이템 식별번호"),
                fieldWithPath("*[].name")
                    .description("아이템 이름"),
                fieldWithPath("*[].itemRankImage")
                    .description("아이템 이미지 파일 이름")
            )
        ));
  }

  @Test
  void read_tierlist_detail_404() throws Exception {

    Long tierlistId = 1L;

    given(tierlistReadUseCase.getTierlist(any(), any()))
        .willThrow(new TierlistNotFoundException());

    mvc.perform(get("/tierlist/{tierlistId}", tierlistId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isNotFound())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            pathParameters(
                parameterWithName("tierlistId")
                    .description("Tierlist ID")
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
  void read_tierlist_detail_403() throws Exception {

    Long tierlistId = 1L;

    given(tierlistReadUseCase.getTierlist(any(), any()))
        .willThrow(new TierlistAuthorizationException());

    mvc.perform(get("/tierlist/{tierlistId}", tierlistId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isForbidden())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
                    .optional()
            ),
            pathParameters(
                parameterWithName("tierlistId")
                    .description("Tierlist ID")
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
