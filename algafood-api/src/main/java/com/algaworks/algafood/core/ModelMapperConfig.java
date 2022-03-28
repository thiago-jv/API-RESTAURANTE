package com.algaworks.algafood.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	// registrando um bean no spring
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
//				.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		var enderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoModelTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
	}

}
