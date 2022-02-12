package com.algaworks.algafood.domain.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RestauranteMixin {

	@JsonIgnore
	private Cozinha cozinha;
		
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private LocalDateTime dataCadastro = LocalDateTime.now();
	
	@JsonIgnore
	private LocalDateTime dataAtualizacao = LocalDateTime.now();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>(); 

	@JsonIgnore
	private List<FormaPagamento> formaPagamentos = new ArrayList<>();
}
