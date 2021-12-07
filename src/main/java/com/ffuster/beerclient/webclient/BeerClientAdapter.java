package com.ffuster.beerclient.webclient;

import com.ffuster.beerclient.model.BeerDTO;
import com.ffuster.beerclient.model.BeerPagedListDTO;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface BeerClientAdapter {

  Mono<BeerPagedListDTO> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle,
      Boolean showInventoryOnhandBoolean);

  Mono<BeerDTO> getBeer(UUID id, Boolean showInventoryOnHand);

  Mono<ResponseEntity<Void>> createBeer(BeerDTO beer);

  Mono<ResponseEntity<Void>> updateBeer(UUID id, BeerDTO beer);

  Mono<ResponseEntity<Void>> deleteBeer(UUID id);

  Mono<BeerDTO> getBeerByUPC(String upc);
}
