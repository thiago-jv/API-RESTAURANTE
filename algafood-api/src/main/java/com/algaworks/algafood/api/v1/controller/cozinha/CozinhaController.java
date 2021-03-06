package com.algaworks.algafood.api.v1.controller.cozinha;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algaworks.algafood.api.v1.assembler.cozinha.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.cozinha.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;
import com.algaworks.algafood.domain.service.cozinha.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController  implements CozinhaControllerOpenApi{
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@GetMapping
	@Override
	public List<CozinhaModel> listar() {
		List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

		return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
	}
	
	@GetMapping("pageable")
	@Override
	public Page<CozinhaModel> listarPageable(Pageable pageable) {
		log.info("Listando cozinhas.");
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		List<CozinhaModel> cozinhaModels = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		
		Page<CozinhaModel> cozinhaModelPage = new PageImpl<>(cozinhaModels, pageable, cozinhasPage.getTotalPages());
		
		return cozinhaModelPage;
		
	}

	@GetMapping("/{cozinhaId}")
	@Override
	public CozinhaModel buscar(@PathVariable("cozinhaId") Long cozinhaId) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
	    cozinha = cadastroCozinhaService.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	@Override
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
	    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	    cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinhaService.excluir(cozinhaId);
	}

}
