package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Restaurante, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Setter
@Getter
public class RestauranteModel {

	private Long id;

	private String nome;

	private BigDecimal taxaFrete;
	
	private Boolean ativo;
	
	private Boolean aberto;
	
	private EnderecoModel endereco;

	private CozinhaModel cozinha;

}
