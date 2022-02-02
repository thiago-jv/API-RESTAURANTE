package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.damain.model.Cozinha;
import com.algaworks.algafood.damain.model.Restaurante;
import com.algaworks.algafood.damain.repository.CozinhaRepository;
import com.algaworks.algafood.damain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId = restaurante.getCozinha().getId();

	    Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
	    
	    if(cozinha == null) {
	    	throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
	    }
	    
	    restaurante.setCozinha(cozinha);
	    
	    
		
		return restauranteRepository.adicionar(restaurante);
	}

	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.remover(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com o código %d", restauranteId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removido, pois está em uso", restauranteId));
		}
	}
}
