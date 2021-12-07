package com.ffuster.beerclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class BeerPagedListDTO extends PageImpl<BeerDTO> implements Serializable {

  static final long serialVersionUID = 1114715135625836949L;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public BeerPagedListDTO(@JsonProperty("content") List<BeerDTO> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements,
      @JsonProperty("pageable") JsonNode pageable,
      @JsonProperty("last") boolean last,
      @JsonProperty("totalPages") int totalPages,
      @JsonProperty("sort") JsonNode sort,
      @JsonProperty("first") boolean first,
      @JsonProperty("numberOfElements") int numberOfElements) {

    super(content, PageRequest.of(number, size), totalElements);
  }

  public BeerPagedListDTO(List<BeerDTO> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public BeerPagedListDTO(List<BeerDTO> content) {
    super(content);
  }

}
