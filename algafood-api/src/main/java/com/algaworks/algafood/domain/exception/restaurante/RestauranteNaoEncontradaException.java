package com.algaworks.algafood.domain.exception.restaurante;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradaException(String mensagem) {
	   super(mensagem);
	}
	
	public RestauranteNaoEncontradaException(Long restauranteId) {
		this(String.format("Restaurante de código %d não pode ser removida, pois esta em uso", restauranteId));
	}

}
