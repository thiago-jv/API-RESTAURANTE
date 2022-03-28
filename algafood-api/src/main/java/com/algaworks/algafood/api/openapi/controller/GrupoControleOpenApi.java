package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControleOpenApi {

	@ApiOperation("Busca todos grupos")
	public List<GrupoModel> listar();
	
	@ApiOperation("Busca grupo por id")
	public GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
	
	@ApiOperation("Adiciona um grupo")
	public GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um gupo") GrupoInput grupoInput);
	
	@ApiOperation("Atualiza grupo por id")
	 @ApiResponses({
		 @ApiResponse(code = 400, message = "ID do grupo invalido", response = Problema.class),
		 @ApiResponse(code = 404, message = "Grupo não encontrado ", response = Problema.class)	 
	 })
	public GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true)
	Long grupoId,
	
	@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", 
		required = true)
	GrupoInput grupoInput);
	
	@ApiOperation("Deleta grupo por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluido"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)		
	})
	public void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true)
	Long grupoId);
	
}
