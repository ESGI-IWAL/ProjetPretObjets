package com.aliw.pretemoica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.aliw.pretemoica")
@EnableJpaRepositories("com.aliw.pretemoica.repository")
public class PretemoicaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PretemoicaApplication.class, args);
  }
}
