package com.algaworks.algafood.damain.repository;

import java.util.List;

import com.algaworks.algafood.damain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);
	
}
