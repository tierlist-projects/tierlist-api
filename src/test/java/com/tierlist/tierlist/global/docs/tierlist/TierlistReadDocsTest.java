package com.tierlist.tierlist.global.docs.tierlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistReadController;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRankResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import com.tierlist.tierlist.topic.application.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistReadController.class)
class TierlistReadDocsTest extends RestDocsTestSupport {

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
            .liked(true)
            .likesCount(13)
            .isMyTierlist(true)
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
                    .description("아이템 이미지 파일 이름"),
                fieldWithPath("liked")
                    .description("사용자가 좋아요를 눌렀는지 여부"),
                fieldWithPath("likesCount")
                    .description("티어리스트 좋아요 갯수"),
                fieldWithPath("myTierlist")
                    .description("사용자가 작성한 티어리스트인지 여부")
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

  @Test
  void read_tierlists_200() throws Exception {

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(tierlistReadUseCase.getTierlists(any(), anyInt(), anyInt(), any(), any()))
        .willReturn(
            List.of(
                TierlistResponse.builder()
                    .id(1L)
                    .title("티어리스트 제목1")
                    .topic(TopicResponse.builder()
                        .id(1L)
                        .name("토픽1")
                        .category(CategoryResponse.builder()
                            .id(1L)
                            .name("카테고리1")
                            .build())
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임1")
                        .profileImage("profile-image-1")
                        .build())
                    .build(),
                TierlistResponse.builder()
                    .id(2L)
                    .title("티어리스트 제목2")
                    .topic(TopicResponse.builder()
                        .id(2L)
                        .name("토픽2")
                        .category(CategoryResponse.builder()
                            .id(2L)
                            .name("카테고리2")
                            .build())
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임2")
                        .profileImage("profile-image-2")
                        .build())
                    .build()
            )
        );

    mvc.perform(get("/tierlist")
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
                    .description("페이지 당 컨텐츠 갯수")
                    .attributes(constraints("1부터 시작")),
                parameterWithName("query")
                    .description("검색어")
                    .optional()
                    .attributes(constraints("2글자 이상")),
                parameterWithName("filter")
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
            ),
            responseFields(
                fieldWithPath("[]")
                    .description("티어리스트 목록"),
                fieldWithPath("[].id")
                    .description("티어리스트 식별번호"),
                fieldWithPath("[].title")
                    .description("티어리스트 제목"),
                fieldWithPath("[].createdAt")
                    .description("티어리스트 생성 시간"),
                fieldWithPath("[].topic")
                    .description("티어리스트가 해당되는 토픽"),
                fieldWithPath("[].topic.id")
                    .description("티어리스트가 해당되는 토픽 식별번호"),
                fieldWithPath("[].topic.name")
                    .description("티어리스트가 해당되는 토픽 이름"),
                fieldWithPath("[].topic.favoriteCount")
                    .description("티어리스트가 해당되는 토픽 즐겨찾기 갯수"),
                fieldWithPath("[].topic.category")
                    .description("토픽이 해당되는 카테고리"),
                fieldWithPath("[].topic.category.id")
                    .description("토픽이 해당되는 카테고리 식별번호"),
                fieldWithPath("[].topic.category.name")
                    .description("토픽이 해당되는 카테고리 이름"),
                fieldWithPath("[].topic.category.favoriteCount")
                    .description("토픽이 해당되는 카테고리 즐겨찾기 갯수"),
                fieldWithPath("[].likesCount")
                    .description("티어리스트 좋아요 갯수"),
                fieldWithPath("[].commentsCount")
                    .description("티어리스트 댓글 갯수"),
                fieldWithPath("[].viewCount")
                    .description("티어리스트 조회수"),
                fieldWithPath("[].writer")
                    .description("티어리스트 작성자"),
                fieldWithPath("[].writer.id")
                    .description("티어리스트 작성자 식별번호"),
                fieldWithPath("[].writer.nickname")
                    .description("티어리스트 작성자 닉네임"),
                fieldWithPath("[].writer.profileImage")
                    .description("티어리스트 작성자 프로필 이미지 파일명")
            )
        ));
  }

  @Test
  void read_tierlists_my_200() throws Exception {

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(tierlistReadUseCase.getMyTierlists(any(), anyInt(), anyInt(), any(), any()))
        .willReturn(
            List.of(
                TierlistResponse.builder()
                    .id(1L)
                    .title("티어리스트 제목1")
                    .isPublished(false)
                    .topic(TopicResponse.builder()
                        .id(1L)
                        .name("토픽1")
                        .category(CategoryResponse.builder()
                            .id(1L)
                            .name("카테고리1")
                            .build())
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .build(),
                TierlistResponse.builder()
                    .id(2L)
                    .title("티어리스트 제목2")
                    .isPublished(true)
                    .topic(TopicResponse.builder()
                        .id(2L)
                        .name("토픽2")
                        .category(CategoryResponse.builder()
                            .id(2L)
                            .name("카테고리2")
                            .build())
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .build()
            )
        );

    mvc.perform(get("/me/tierlist")
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
                    .description("페이지 당 컨텐츠 갯수")
                    .attributes(constraints("1부터 시작")),
                parameterWithName("query")
                    .description("검색어")
                    .optional()
                    .attributes(constraints("2글자 이상")),
                parameterWithName("filter")
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
            ),
            responseFields(
                fieldWithPath("[]")
                    .description("티어리스트 목록"),
                fieldWithPath("[].id")
                    .description("티어리스트 식별번호"),
                fieldWithPath("[].title")
                    .description("티어리스트 제목"),
                fieldWithPath("[].createdAt")
                    .description("티어리스트 생성 시간"),
                fieldWithPath("[].isPublished")
                    .description("티어리스트 PUBLISH 여부"),
                fieldWithPath("[].topic")
                    .description("티어리스트가 해당되는 토픽"),
                fieldWithPath("[].topic.id")
                    .description("티어리스트가 해당되는 토픽 식별번호"),
                fieldWithPath("[].topic.name")
                    .description("티어리스트가 해당되는 토픽 이름"),
                fieldWithPath("[].topic.category")
                    .description("토픽이 해당되는 카테고리"),
                fieldWithPath("[].topic.favoriteCount")
                    .description("티어리스트가 해당되는 토픽 즐겨찾기 갯수"),
                fieldWithPath("[].topic.category.id")
                    .description("토픽이 해당되는 카테고리 식별번호"),
                fieldWithPath("[].topic.category.favoriteCount")
                    .description("토픽이 해당되는 카테고리 즐겨찾기 갯수"),
                fieldWithPath("[].topic.category.name")
                    .description("토픽이 해당되는 카테고리 이름"),
                fieldWithPath("[].likesCount")
                    .description("티어리스트 좋아요 갯수"),
                fieldWithPath("[].commentsCount")
                    .description("티어리스트 댓글 갯수"),
                fieldWithPath("[].viewCount")
                    .description("티어리스트 조회수")
            )
        ));
  }

  @Test
  void read_tierlists_of_category_200() throws Exception {

    Long categoryId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(
        tierlistReadUseCase.getTierlistsOfCategory(any(), any(), anyInt(), anyInt(), any(), any()))
        .willReturn(
            List.of(
                TierlistResponse.builder()
                    .id(1L)
                    .title("티어리스트 제목1")
                    .topic(TopicResponse.builder()
                        .id(1L)
                        .name("토픽1")
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임1")
                        .profileImage("profile-image-1")
                        .build())
                    .build(),
                TierlistResponse.builder()
                    .id(2L)
                    .title("티어리스트 제목2")
                    .topic(TopicResponse.builder()
                        .id(2L)
                        .name("토픽2")
                        .build())
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임2")
                        .profileImage("profile-image-2")
                        .build())
                    .build()
            )
        );

    mvc.perform(get("/category/{categoryId}/tierlist", categoryId)
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
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
            ),
            responseFields(
                fieldWithPath("[]")
                    .description("티어리스트 목록"),
                fieldWithPath("[].id")
                    .description("티어리스트 식별번호"),
                fieldWithPath("[].title")
                    .description("티어리스트 제목"),
                fieldWithPath("[].createdAt")
                    .description("티어리스트 생성 시간"),
                fieldWithPath("[].topic")
                    .description("티어리스트가 해당되는 토픽"),
                fieldWithPath("[].topic.favoriteCount")
                    .description("티어리스트가 해당되는 토픽 즐겨찾기 갯수"),
                fieldWithPath("[].topic.id")
                    .description("티어리스트가 해당되는 토픽 식별번호"),
                fieldWithPath("[].topic.name")
                    .description("티어리스트가 해당되는 토픽 이름"),
                fieldWithPath("[].likesCount")
                    .description("티어리스트 좋아요 갯수"),
                fieldWithPath("[].commentsCount")
                    .description("티어리스트 댓글 갯수"),
                fieldWithPath("[].viewCount")
                    .description("티어리스트 조회수"),
                fieldWithPath("[].writer")
                    .description("티어리스트 작성자"),
                fieldWithPath("[].writer.id")
                    .description("티어리스트 작성자 식별번호"),
                fieldWithPath("[].writer.nickname")
                    .description("티어리스트 작성자 닉네임"),
                fieldWithPath("[].writer.profileImage")
                    .description("티어리스트 작성자 프로필 이미지 파일명")
            )
        ));
  }

  @Test
  void read_tierlists_of_category_404() throws Exception {

    Long categoryId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(
        tierlistReadUseCase.getTierlistsOfCategory(any(), any(), anyInt(), anyInt(), any(), any()))
        .willThrow(new CategoryNotFoundException());

    mvc.perform(get("/category/{categoryId}/tierlist", categoryId)
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
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
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
  void read_tierlists_of_topic_200() throws Exception {

    Long topicId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(
        tierlistReadUseCase.getTierlistsOfTopic(any(), any(), anyInt(), anyInt(), any(), any()))
        .willReturn(
            List.of(
                TierlistResponse.builder()
                    .id(1L)
                    .title("티어리스트 제목1")
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임1")
                        .profileImage("profile-image-1")
                        .build())
                    .build(),
                TierlistResponse.builder()
                    .id(2L)
                    .title("티어리스트 제목2")
                    .createdAt(LocalDateTime.of(2024, 4, 23, 20, 17, 15))
                    .likesCount(19)
                    .commentsCount(10)
                    .viewCount(200)
                    .writer(MemberResponse.builder()
                        .id(1L)
                        .nickname("닉네임2")
                        .profileImage("profile-image-2")
                        .build())
                    .build()
            )
        );

    mvc.perform(get("/topic/{topicId}/tierlist", topicId)
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
            pathParameters(
                parameterWithName("topicId")
                    .description("Topic ID")
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
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
            ),
            responseFields(
                fieldWithPath("[]")
                    .description("티어리스트 목록"),
                fieldWithPath("[].id")
                    .description("티어리스트 식별번호"),
                fieldWithPath("[].title")
                    .description("티어리스트 제목"),
                fieldWithPath("[].createdAt")
                    .description("티어리스트 생성 시간"),
                fieldWithPath("[].likesCount")
                    .description("티어리스트 좋아요 갯수"),
                fieldWithPath("[].commentsCount")
                    .description("티어리스트 댓글 갯수"),
                fieldWithPath("[].viewCount")
                    .description("티어리스트 조회수"),
                fieldWithPath("[].writer")
                    .description("티어리스트 작성자"),
                fieldWithPath("[].writer.id")
                    .description("티어리스트 작성자 식별번호"),
                fieldWithPath("[].writer.nickname")
                    .description("티어리스트 작성자 닉네임"),
                fieldWithPath("[].writer.profileImage")
                    .description("티어리스트 작성자 프로필 이미지 파일명")
            )
        ));
  }

  @Test
  void read_tierlists_of_topic_404() throws Exception {

    Long topicId = 1L;

    int pageCount = 1;
    int pageSize = 10;
    String query = "qqq";
    TierlistFilter filter = TierlistFilter.HOT;

    given(
        tierlistReadUseCase.getTierlistsOfTopic(any(), any(), anyInt(), anyInt(), any(), any()))
        .willThrow(new TopicNotFoundException());

    mvc.perform(get("/topic/{topicId}/tierlist", topicId)
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
                parameterWithName("topicId")
                    .description("Topic ID")
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
                    .description("정렬 필터")
                    .attributes(constraints("RECENT, HOT만 가능"))
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
