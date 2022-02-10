package com.algaworks.algafood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	// trata erro de sintaxe no código
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable causaRaisProblema = ExceptionUtils.getRootCause(ex);
		
		if(causaRaisProblema instanceof InvalidFormatException) {
			 return handleInvalidFormatException((InvalidFormatException) causaRaisProblema, headers, status, request);
	    } else if (causaRaisProblema instanceof PropertyBindingException) {
	        return handlePropertyBindingException((PropertyBindingException) causaRaisProblema, headers, status, request); 
	    }
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problema problema = createProblemaBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// trata se os dados repassados são validos, no caso por exemplo, verifica se tem uma , a mais
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
	    
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
	    String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problema problema = createProblemaBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// trata uma entidade não encontrada
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoProblema tipoProblema  = TipoProblema.ENTIDADE_NAO_ENCONTRADA;

		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	// trata quando uma entidade está em uso
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
		
		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	// trata erros de negocio 
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
		
		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	// 1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException

	// 2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente

	// 3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
//	    é uma instância de MethodArgumentTypeMismatchException

	// 4. Se for, chamamos um método especialista em tratar esse tipo de exception

	// 5. Poderíamos fazer tudo dentro de handleTypeMismatch, mas preferi separar em outro método
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
	    
	    if (ex instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) ex, headers, status, request);
	    }

	    return super.handleTypeMismatch(ex, headers, status, request);
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
	
	private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
	    
		return Problema.builder().status(status.value())
				.type(tipoProblema.getUri())
				.title(tipoProblema.getTitulo())
				.detail(detail);
	}
	
	
	// trata os campos existem ou não
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    // Criei o método joinPath para reaproveitar em todos os métodos que precisam
	    // concatenar os nomes das propriedades (separando por ".")
	    String path = joinPath(ex.getPath());
	    
	    TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
	    String detail = String.format("A propriedade '%s' não existe. "
	            + "Corrija ou remova essa propriedade e tente novamente.", path);

	    Problema problema = createProblemaBuilder(status, tipoProblema, detail).build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}  
	
	// trata uma url válida
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problema problema = createProblemaBuilder(status, tipoProblema, detail).build();

	    return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	private String joinPath(java.util.List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}
	
}
