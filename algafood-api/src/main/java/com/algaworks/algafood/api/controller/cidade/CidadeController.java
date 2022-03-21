package com.algaworks.algafood.api.controller.cidade;

import java.io.Serializable;
import java.util.List;

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

import com.algaworks.algafood.api.assembler.cidade.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.cidade.CidadeModelAssembler;
import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.estado.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.cidade.CidadeRepository;
import com.algaworks.algafood.domain.service.cidade.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidade")
@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@ApiOperation("Busca todas cidades sem paginação")
	@GetMapping
	public List<CidadeModel> buscar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@ApiOperation("Busca cidade por id sem paginação")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@ApiOperation("Adiciona uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidadeService.salvar(cidade);

			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza cidade por id")
	 @ApiResponses({
		 @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problema.class),
		 @ApiResponse(code = 404, message = "Cidade não encontrada ", response = Problema.class)	 
	 })
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId,  
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
	        @RequestBody CidadeInput cidadeInput) {
		try {

			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);

		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@ApiOperation("Deleta uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluida"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
		
	})
	@DeleteMapping("/{cidadeId}")
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		cadastroCidadeService.excluir(cidadeId);
	}

}
