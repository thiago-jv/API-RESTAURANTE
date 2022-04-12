package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Busca todas cidades sem paginação")
	public List<CidadeModel> buscar();
	
	@ApiOperation("Busca cidade por id sem paginação")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

	@ApiOperation("Adiciona uma cidade")
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CidadeInput cidadeInput);

	@ApiOperation("Atualiza cidade por id")
	 @ApiResponses({
		 @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problema.class),
		 @ApiResponse(code = 404, message = "Cidade não encontrada ", response = Problema.class)	 
	 })
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId,  
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
	        CidadeInput cidadeInput);
	
	@ApiOperation("Deleta uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluida"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)		
	})
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

}