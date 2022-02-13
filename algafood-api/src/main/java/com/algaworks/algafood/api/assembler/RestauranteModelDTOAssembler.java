package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CozinhaModelDTO;
import com.algaworks.algafood.api.model.RestauranteModelDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelDTOAssembler {

	public RestauranteModelDTO toModelDTO(Restaurante restaurante) {
		RestauranteModelDTO restauranteModelDTO = new RestauranteModelDTO();
		   CozinhaModelDTO cozinhaModelDTO = new CozinhaModelDTO();
		   
		   cozinhaModelDTO.setId(restaurante.getCozinha().getId());
		   cozinhaModelDTO.setNome(restaurante.getCozinha().getNome());
		   
		   restauranteModelDTO.setId(restaurante.getId());
		   restauranteModelDTO.setNome(restaurante.getNome());
		   restauranteModelDTO.setTaxaFrete(restaurante.getTaxaFrete());
		   restauranteModelDTO.setCozinha(cozinhaModelDTO);
		return restauranteModelDTO;
	}

	public List<RestauranteModelDTO> toCollectionModelDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModelDTO(restaurante))
				.collect(Collectors.toList());
	}
}
