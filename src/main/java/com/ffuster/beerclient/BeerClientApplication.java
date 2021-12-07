package com.ffuster.beerclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BeerClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeerClientApplication.class, args);
  }

}
