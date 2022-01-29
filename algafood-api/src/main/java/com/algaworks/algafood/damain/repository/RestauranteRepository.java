package com.algaworks.algafood.damain.repository;

import java.util.List;

import com.algaworks.algafood.damain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);

}
