package com.algaworks.algafood.domain.service.cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.cidade.CidadeRepository;
import com.algaworks.algafood.domain.repository.estado.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não exisrte cadastro de estado com o código %d", estadoId));
		}
		
		cidade.setEstado(estado);
		
		return cidadeRepository.salvar(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
		 } catch (EmptyResultDataAccessException e) {
             throw new EntidadeNaoEncontradaException(
                 String.format("Não existe um cadastro de cidade com código %d", cidadeId));
         
         } catch (DataIntegrityViolationException e) {
             throw new EntidadeEmUsoException(
                 String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
         }
	}
}
