package com.algaworks.algafood.api.exceptionhandler;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoProblema tipoProblema  = TipoProblema.ENTIDADE_NAO_ENCONTRADA;

		Problema problema = createPoblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
		
		Problema problema = createPoblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
		
		Problema problema = createPoblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problema.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problema.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema.ProblemaBuilder createPoblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
	    
		return Problema.builder().status(status.value())
				.type(tipoProblema.getUri())
				.title(tipoProblema.getTitulo())
				.detail(detail);
	}
	
}
