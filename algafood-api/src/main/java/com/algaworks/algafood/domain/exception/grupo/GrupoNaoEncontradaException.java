package com.algaworks.algafood.domain.exception.grupo;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class GrupoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public GrupoNaoEncontradaException(Long grupoId) {
        this(String.format("Não existe um cadastro de grupo com código %d", grupoId));
    }

}
