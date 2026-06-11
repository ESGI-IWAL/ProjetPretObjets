package com.aliw.pretemoica;

import com.aliw.pretemoica.config.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(UploadProperties.class)
public class PretemoicaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PretemoicaApplication.class, args);
  }
}
