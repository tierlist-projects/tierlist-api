package com.tierlist.tierlist.image.application.domain.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageFile {

  private static final String EXTENSION_DELIMITER = ".";

  private final MultipartFile file;
  private final String hashName;

  public ImageFile(MultipartFile file) {
    this.file = file;
    this.hashName = hashName(file);
  }

  public String getContentType() {
    return this.file.getContentType();
  }

  public long getSize() {
    return this.file.getSize();
  }

  public byte[] getBytes() throws IOException {
    return this.file.getBytes();
  }

  private String hashName(MultipartFile file) {
    final String name = file.getOriginalFilename();
    final String filenameExtension = StringUtils.getFilenameExtension(name);
    final String nameAndDate = name + LocalDateTime.now();

    try {
      MessageDigest hashAlgorithm = MessageDigest.getInstance("SHA3-256");
      byte[] hashBytes = hashAlgorithm.digest(nameAndDate.getBytes(StandardCharsets.UTF_8));
      return new String(Hex.encode(hashBytes)) + EXTENSION_DELIMITER + filenameExtension;

    } catch (final NoSuchAlgorithmException e) {
      throw new RuntimeException();
    }
  }

}
