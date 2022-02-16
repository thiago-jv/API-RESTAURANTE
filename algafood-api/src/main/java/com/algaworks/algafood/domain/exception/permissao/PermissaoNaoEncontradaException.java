package com.algaworks.algafood.domain.exception.permissao;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissão com código %d", permissaoId));
    }   
}
