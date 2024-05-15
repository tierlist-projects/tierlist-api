package com.tierlist.tierlist.global.docs.topic;

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

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.topic.adapter.in.web.TopicFavoriteController;
import com.tierlist.tierlist.topic.application.domain.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.port.in.service.TopicFavoriteUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TopicFavoriteController.class)
class TopicFavoriteDocsTest extends RestDocsTestSupport {

  @MockBean
  private TopicFavoriteUseCase topicFavoriteUseCase;

  @Test
  void toggle_topic_favorite_200() throws Exception {

    Long topicId = 1L;

    mvc.perform(patch("/topic/{topicId}/favorite/toggle", topicId)
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
                parameterWithName("topicId").description("Topic ID")
            )
        ));
  }

  @Test
  void toggle_topic_favorite_404() throws Exception {

    Long topicId = 1L;

    doThrow(new TopicNotFoundException())
        .when(topicFavoriteUseCase).toggleFavorite(any(), any());

    mvc.perform(patch("/topic/{topicId}/favorite/toggle", topicId)
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
                parameterWithName("topicId").description("Topic ID")
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
