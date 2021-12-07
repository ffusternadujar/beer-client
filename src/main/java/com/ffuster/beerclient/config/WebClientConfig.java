package com.ffuster.beerclient.config;

import io.netty.handler.logging.LogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
public class WebClientConfig {

  @Bean
  @ConfigurationProperties(prefix = "client.rest.beer-client")
  public WebClientProperties beerWebClientProperties() {
    return WebClientProperties.builder().build();
  }

  @Bean
  public WebClient webClient(WebClientProperties beerWebClientProperties, ReactorClientHttpConnector reactorClientHttpConnector) {
    return WebClient.builder()
        .baseUrl(beerWebClientProperties.getBaseUrl())
        .clientConnector(reactorClientHttpConnector)
        .build();
  }

  @Bean
  public ReactorClientHttpConnector reactorClientHttpConnector() {
    return new ReactorClientHttpConnector(HttpClient.create()
        .wiretap("reactor.netty.client.HttpClient", LogLevel.TRACE,
            AdvancedByteBufFormat.TEXTUAL));
  }
}
