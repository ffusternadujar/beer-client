package com.ffuster.beerclient.webclient;

import com.ffuster.beerclient.config.WebClientProperties;
import com.ffuster.beerclient.model.BeerDTO;
import com.ffuster.beerclient.model.BeerPagedListDTO;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BeerClientAdapterImpl implements BeerClientAdapter {

  private final WebClient webClient;

  @Override
  public Mono<BeerPagedListDTO> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle,
      Boolean showInventoryOnhandBoolean) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(WebClientProperties.BEER_V1_PATH)
            .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
            .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
            .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
            .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
            .queryParamIfPresent("showInventoryOnhandBoolean", Optional.ofNullable(showInventoryOnhandBoolean))
            .build())
        .retrieve()
        .bodyToMono(BeerPagedListDTO.class);
  }

  @Override
  public Mono<BeerDTO> getBeer(UUID id, Boolean showInventoryOnHand) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_GET_BY_ID_PATH)
            .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
            .build(id))
        .retrieve()
        .bodyToMono(BeerDTO.class);
  }

  @Override
  public Mono<ResponseEntity<Void>> createBeer(BeerDTO beer) {
    return webClient.post()
        .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
        .body(BodyInserters.fromValue(beer))
        .retrieve()
        .toBodilessEntity();
  }

  @Override
  public Mono<ResponseEntity<Void>> updateBeer(UUID id, BeerDTO beer) {
    return webClient.put()
        .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_GET_BY_ID_PATH).build(id))
        .body(BodyInserters.fromValue(beer))
        .retrieve()
        .toBodilessEntity();
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteBeer(UUID id) {
    return webClient.delete()
        .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_GET_BY_ID_PATH).build(id))
        .retrieve()
        .toBodilessEntity();
  }

  @Override
  public Mono<BeerDTO> getBeerByUPC(String upc) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_UPC_PATH)
            .build(upc))
        .retrieve()
        .bodyToMono(BeerDTO.class);
  }
}







