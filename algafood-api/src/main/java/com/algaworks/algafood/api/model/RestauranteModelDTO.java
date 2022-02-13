package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RestauranteModelDTO {

	private Long id;
	
	private String nome;
	
	private BigDecimal taxaFrete;
	
    private CozinhaModelDTO cozinha;
    
	
}
