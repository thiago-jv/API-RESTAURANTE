package com.algaworks.algafood.domain.exception.cidade;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String mensagem) {
	   super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Cidade de código %d não pode ser removida, pois esta em uso", cidadeId));
	}

}
