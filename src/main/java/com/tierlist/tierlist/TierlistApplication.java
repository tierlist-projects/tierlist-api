package com.tierlist.tierlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TierlistApplication {

  public static void main(String[] args) {
    SpringApplication.run(TierlistApplication.class, args);
  }

}
