package com.tierlist.tierlist.global.s3;


import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Slf4j
@Configuration
public class S3Config {

  @Value("${aws.s3.region}")
  private String region;
  @Value("${aws.s3.access-key}")
  private String accessKey;
  @Value("${aws.s3.secret-key}")
  private String secretKey;
  @Value("${aws.s3.endpoint-uri}")
  private String endpointUri;

  @Bean
  public AwsCredentials AwsCredentials() {
    return AwsBasicCredentials.create(accessKey, secretKey);
  }

  @Bean
  public S3Client s3Client(AwsCredentials awsCredentials) {
    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
        .endpointOverride(URI.create(endpointUri))
        .build();
  }


}
