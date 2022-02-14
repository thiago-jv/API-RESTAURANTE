package com.algaworks.algafood.api.assembler.restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	// modelMapper não é um componente do spring, se faz necessário criar uma classe de configuração ModelMapperConfig
	
	@Autowired
	private ModelMapper modelMapper;

	// Método responsável por pegar a Classe RestauranteInput, que contém as
	// validações e converter para a classe Restaurante
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Cozinha was altered from 13 to 25
		restaurante.setCozinha(new Cozinha());

		modelMapper.map(restauranteInput, restaurante);
	}
}
