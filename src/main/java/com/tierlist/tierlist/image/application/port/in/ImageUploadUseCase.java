package com.tierlist.tierlist.image.application.port.in;

import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadUseCase {

  ImagesResponse upload(List<MultipartFile> images);
}
