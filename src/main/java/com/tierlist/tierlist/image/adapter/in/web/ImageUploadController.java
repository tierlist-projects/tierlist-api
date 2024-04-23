package com.tierlist.tierlist.image.adapter.in.web;

import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import com.tierlist.tierlist.image.application.port.in.ImageUploadUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RequestMapping("/image")
@RestController
public class ImageUploadController {

  private final ImageUploadUseCase imageUploadUseCase;

  @PostMapping
  public ResponseEntity<ImagesResponse> upload(
      @RequestPart(name = "image") List<MultipartFile> images) {
    ImagesResponse response = imageUploadUseCase.upload(images);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
