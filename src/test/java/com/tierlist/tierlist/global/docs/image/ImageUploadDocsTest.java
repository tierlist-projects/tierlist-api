package com.tierlist.tierlist.global.docs.image;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tierlist.tierlist.global.docs.RestDocsTestSupport;
import com.tierlist.tierlist.global.support.security.WithMockMember;
import com.tierlist.tierlist.image.adapter.in.web.ImageUploadController;
import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import com.tierlist.tierlist.image.application.exception.ImageUploadException;
import com.tierlist.tierlist.image.application.port.out.ImageUploadUseCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(ImageUploadController.class)
class ImageUploadDocsTest extends RestDocsTestSupport {


  @MockBean
  private ImageUploadUseCase imageUploadUseCase;

  @WithMockMember
  @Test
  void upload_image_201() throws Exception {

    ImagesResponse imagesResponse = new ImagesResponse(List.of(
        "image-name-1",
        "image-name-2",
        "image-name-3"
    ));

    given(imageUploadUseCase.upload(any())).willReturn(imagesResponse);

    mvc.perform(multipart("/image")
            .file(new MockMultipartFile("image", new byte[]{}))
            .file(new MockMultipartFile("image", new byte[]{}))
            .file(new MockMultipartFile("image", new byte[]{}))
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isCreated())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestParts(
                partWithName("image")
                    .description("업로드할 이미지 리스트")
                    .attributes(constraints("파일 당 최대 용량은 10MB, 전체 최대 용량은 100MB이다."))
            ),
            responseFields(
                fieldWithPath("imageNames").description("저장된 이미지 파일명 리스트")
            )
        ));
  }

  @WithMockMember
  @Test
  void upload_image_503() throws Exception {

    given(imageUploadUseCase.upload(any())).willThrow(new ImageUploadException());

    mvc.perform(multipart("/image")
            .file(new MockMultipartFile("image", new byte[]{}))
            .contentType(APPLICATION_JSON)
            .header("Access-Token", "sample.access.token")
        )
        .andExpect(status().isServiceUnavailable())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName("Access-Token")
                    .description("JWT Access Token")
            ),
            requestParts(
                partWithName("image").description("업로드할 이미지 리스트")
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
