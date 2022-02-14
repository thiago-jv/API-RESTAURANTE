package com.algaworks.algafood.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	// registrando um bean no spring
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
