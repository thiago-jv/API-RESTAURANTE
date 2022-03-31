package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Busca todas formas pagamentos sem paginação")
	public ResponseEntity<List<FormaPagamentoModel>> listar();
	
	@ApiOperation("Busca forma pagamento por id sem paginação")
    public ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de agamento", example = "1") Long formaPagamentoId);
    
	@ApiOperation("Adiciona uma forma de pagamento")
    public FormaPagamentoModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", 
    		required = true) FormaPagamentoInput formaPagamentoInput);
    
	@ApiOperation("Atualiza forma de pagamento por id")
	 @ApiResponses({
		 @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problema.class),
		 @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)	 
	 })
    public FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
	Long formaPagamentoId,
	
	@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", 
		required = true)
            FormaPagamentoInput formaPagamentoInput);
   
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluida"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)		
	})
    public void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
	Long formaPagamentoId);
    
}
