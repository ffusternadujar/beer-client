package com.ffuster.beerclient.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebClientProperties {

  public static final String BEER_V1_PATH = "/api/v1/beer";
  public static final String BEER_V1_GET_BY_ID_PATH = "/api/v1/beer/{uuid}";
  public static final String BEER_V1_UPC_PATH = "/api/v1/beerUpc/{upc}";
  private String baseUrl;
}
