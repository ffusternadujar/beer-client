package com.ffuster.beerclient.webclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ffuster.beerclient.model.BeerDTO;
import com.ffuster.beerclient.model.BeerPagedListDTO;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@SpringBootTest
class BeerClientAdapterImplTest {

  @Autowired
  BeerClientAdapter beerClientAdapter;

  @Test
  void listBeers() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(null, null, null, null, null);

    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();

    assertNotNull(beerPagedListDTO);
    assertThat(beerPagedListDTO.getContent().size()).isGreaterThan(0);
    System.out.println(beerPagedListDTO.toList());
  }

  @Test
  void listBeersPageSize10() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(1, 10, null, null, null);

    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();

    assertNotNull(beerPagedListDTO);
    assertThat(beerPagedListDTO.getContent().size()).isEqualTo(10);
  }

  @Test
  void listBeersPageNoRecords() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(10, 20, null, null, null);

    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();

    assertNotNull(beerPagedListDTO);
    assertThat(beerPagedListDTO.getContent().size()).isEqualTo(0);
  }

  @Test
  void getBeer() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(null, null, null, null, null);
    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();
    BeerDTO beer = beerPagedListDTO.getContent().get(0);

    Mono<BeerDTO> beerDTOMono = beerClientAdapter.getBeer(beer.getId(), true);
    BeerDTO beerDTO = beerDTOMono.block();

    assertNotNull(beerDTO);
    assertEquals(beer.getBeerName(), beerDTO.getBeerName());
    assertEquals(beer.getBeerStyle(), beerDTO.getBeerStyle());
    assertEquals(beer.getUpc(), beerDTO.getUpc());
    assertEquals(beer.getPrice(), beerDTO.getPrice());
  }

  @Test
  void createBeer() {
    BeerDTO beerDTO = BeerDTO.builder()
        .beerName("Dogfishhead 90 Min IPA")
        .beerStyle("IPA")
        .upc("234848549559")
        .price(new BigDecimal("10.99"))
        .build();

    Mono<ResponseEntity<Void>> createBeerMono = beerClientAdapter.createBeer(beerDTO);
    ResponseEntity responseEntity = createBeerMono.block();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  void updateBeer() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(null, null, null, null, null);
    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();

    BeerDTO beer = beerPagedListDTO.getContent().get(0);

    BeerDTO beerUpdated = BeerDTO.builder()
        .beerName("Really Good Beer")
        .beerStyle(beer.getBeerStyle())
        .price(beer.getPrice())
        .upc(beer.getUpc())
        .build();

    Mono<ResponseEntity<Void>> responseEntityMono = beerClientAdapter.updateBeer(beer.getId(), beerUpdated);
    ResponseEntity<Void> responseEntity = responseEntityMono.block();

    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

  }

  @Test
  void deleteBeerNotFound() {
    Mono<ResponseEntity<Void>> responseEntityMono =  beerClientAdapter.deleteBeer(UUID.randomUUID());
    ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable -> {
      if(throwable instanceof WebClientResponseException) {
        WebClientResponseException exception = (WebClientResponseException) throwable;
        return Mono.just(ResponseEntity.status(exception.getStatusCode()).build());
      } else {
        throw new RuntimeException(throwable);
      }
    }).block();
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

  }

  @Test
  void testDeleteBeerHnadleException() {
    Mono<ResponseEntity<Void>> responseEntityMono =  beerClientAdapter.deleteBeer(UUID.randomUUID());
    assertThrows(WebClientResponseException.NotFound.class, () -> responseEntityMono.block());

  }

  @Test
  void deleteBeer() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(null, null, null, null, null);
    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();
    BeerDTO beer = beerPagedListDTO.getContent().get(0);

    Mono<ResponseEntity<Void>> responseEntityMono =  beerClientAdapter.deleteBeer(beer.getId());
    ResponseEntity<Void> responseEntity = responseEntityMono.block();

    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }
  @Test
  void getBeerByUPC() {
    Mono<BeerPagedListDTO> beerPagedListDTOMono = beerClientAdapter.listBeers(null, null, null, null, null);
    BeerPagedListDTO beerPagedListDTO = beerPagedListDTOMono.block();
    BeerDTO beer = beerPagedListDTO.getContent().get(0);

    Mono<BeerDTO> beerDTOMono = beerClientAdapter.getBeerByUPC(beer.getUpc());
    BeerDTO beerDTO = beerDTOMono.block();

    assertNotNull(beerDTO);
    assertEquals(beer.getBeerName(), beerDTO.getBeerName());
    assertEquals(beer.getBeerStyle(), beerDTO.getBeerStyle());
    assertEquals(beer.getUpc(), beerDTO.getUpc());
    assertEquals(beer.getPrice(), beerDTO.getPrice());
  }
}