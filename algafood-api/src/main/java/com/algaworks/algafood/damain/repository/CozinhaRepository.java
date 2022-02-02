package com.algaworks.algafood.damain.repository;

import java.util.List;

import com.algaworks.algafood.damain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Long id);
	
}
