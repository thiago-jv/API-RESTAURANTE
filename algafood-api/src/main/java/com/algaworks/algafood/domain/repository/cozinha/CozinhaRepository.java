package com.algaworks.algafood.domain.repository.cozinha;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Long id);
	
}
