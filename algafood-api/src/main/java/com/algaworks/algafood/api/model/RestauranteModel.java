package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Restaurante, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Setter
@Getter
public class RestauranteModel {

	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@JsonView(RestauranteView.Resumo.class)
	private String nome;

	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	private Boolean ativo;
	
	private Boolean aberto;
	
	private EnderecoModel endereco;

	private CozinhaModel cozinha;

}
