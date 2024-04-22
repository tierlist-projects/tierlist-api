package com.tierlist.tierlist.global.docs.tierlist;

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
import com.tierlist.tierlist.tierlist.adapter.in.web.TierlistPublishController;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistPublishUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TierlistPublishController.class)
class TierlistPublishDocsTest extends RestDocsTestSupport {

  @MockBean
  private TierlistPublishUseCase tierlistPublishUseCase;

  @Test
  void toggle_tierlist_publish_200() throws Exception {

    Long tierlistId = 1L;

    mvc.perform(patch("/tierlist/{tierlistId}/publish/toggle", tierlistId)
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
                parameterWithName("tierlistId").description("Tierlist ID")
            )
        ));
  }

  @Test
  void toggle_tierlist_publish_404() throws Exception {

    Long tierlistId = 1L;

    doThrow(new TierlistNotFoundException())
        .when(tierlistPublishUseCase).togglePublish(any(), any());

    mvc.perform(patch("/tierlist/{tierlistId}/publish/toggle", tierlistId)
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
  void toggle_tierlist_publish_403() throws Exception {

    Long tierlistId = 1L;

    doThrow(new TierlistAuthorizationException())
        .when(tierlistPublishUseCase).togglePublish(any(), any());

    mvc.perform(patch("/tierlist/{tierlistId}/publish/toggle", tierlistId)
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isForbidden())
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
}
