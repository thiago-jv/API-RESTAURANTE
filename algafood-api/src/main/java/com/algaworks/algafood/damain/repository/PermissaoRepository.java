package com.algaworks.algafood.damain.repository;

import java.util.List;

import com.algaworks.algafood.damain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);
}
