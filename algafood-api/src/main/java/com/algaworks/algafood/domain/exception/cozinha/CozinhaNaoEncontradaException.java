package com.algaworks.algafood.domain.exception.cozinha;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String mensagem) {
	   super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Cozinha de código %d não pode ser removida, pois esta em uso", cozinhaId));
	}

}
