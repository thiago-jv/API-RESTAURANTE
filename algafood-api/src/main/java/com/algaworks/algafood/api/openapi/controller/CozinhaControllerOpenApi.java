package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas sem paginação")
    public List<CozinhaModel> listar();
    
    @ApiOperation("Lista as cozinhas com paginação")
    public Page<CozinhaModel> listarPageable(Pageable pageable);
    
    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    public CozinhaModel buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);
    
    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    public CozinhaModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
            CozinhaInput cozinhaInput);
    
    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cozinha atualizada"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    public CozinhaModel atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId,
            
            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
            CozinhaInput cozinhaInput);
    
    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cozinha excluída"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    public void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);        

}
