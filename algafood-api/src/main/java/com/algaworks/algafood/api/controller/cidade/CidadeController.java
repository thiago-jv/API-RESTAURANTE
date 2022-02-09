package com.algaworks.algafood.api.controller.cidade;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.cidade.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId){	
		return cadastroCidadeService.buscarOuFalhar(cidadeId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade){
		return cadastroCidadeService.salvar(cidade);
	}
	
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
		Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		return cadastroCidadeService.salvar(cidadeAtual);
	}
	
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
	}

}
