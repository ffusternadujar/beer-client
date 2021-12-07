package com.ffuster.beerclient.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

  private UUID id;

  private String beerName;

  private String beerStyle;

  private String upc;

  private BigDecimal price;

  private Integer quantityOnHand;

  private OffsetDateTime createdDate;

  private OffsetDateTime lastUpdatedDate;

}
