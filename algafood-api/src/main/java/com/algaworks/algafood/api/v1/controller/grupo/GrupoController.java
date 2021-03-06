package com.algaworks.algafood.api.v1.controller.grupo;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.v1.assembler.grupo.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.grupo.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControleOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.grupo.GrupoRepository;
import com.algaworks.algafood.domain.service.grupo.GrupoService;

@RestController
@RequestMapping(path =  "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControleOpenApi{

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoModelAssembler grupoModelAssempler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	@Override
	public List<GrupoModel> listar() {
		List<Grupo> grupos = grupoRepository.findAll();

		return grupoModelAssempler.toCollectionModel(grupos);
	}

	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return grupoModelAssempler.toModel(grupo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

		grupo = grupoService.salvar(grupo);

		return grupoModelAssempler.toModel(grupo);
	}

	@PutMapping("/{grupoId}")
	@Override
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);

		grupoInputDisassembler.copyToDomain(grupoInput, grupoAtual);

		grupoAtual = grupoService.salvar(grupoAtual);

		return grupoModelAssempler.toModel(grupoAtual);

	}
	
	@DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void remover(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);	
    }   

}
