package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
	+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";
	
	// trata mediaTypes não permitidas
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

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
		
		Problema problema = createProblemaBuilder(status, tipoProblema, detail)
				.mensagemUsuario(detail)
				.build();
		
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
		
		Problema problema = createProblemaBuilder(status, tipoProblema, detail)
				.mensagemUsuario(detail)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// trata uma entidade não encontrada
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoProblema tipoProblema  = TipoProblema.ENTIDADE_NAO_ENCONTRADA;

		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
				.mensagemUsuario(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	// trata quando uma entidade está em uso
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
		
		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
				.mensagemUsuario(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), 
				status, request);
	}
	
	// trata erros de negocio 
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
		
		Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
				.mensagemUsuario(ex.getMessage())
				.build();
		
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
	
	
	// tratamento interno customizado
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problema.builder()
					.dataHora(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		} else if (body instanceof String) {
			body = Problema.builder()
					.dataHora(OffsetDateTime.now())
					.title((String) body)
					.status(status.value())
					.mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
	    
		
		return Problema.builder().status(status.value())
				.dataHora(OffsetDateTime.now())
				.status(status.value())
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

	    Problema problema = createProblemaBuilder(status, tipoProblema, detail)
	    		.mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
	    		.build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}  
	
	// trata paramentros invalidos de uma URI
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problema problema = createProblemaBuilder(status, tipoProblema, detail)
	    		.mensagemUsuario(detail)
	    		.build();

	    return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// trata erro de recurso não valido
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
	    TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
	    String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
	            ex.getRequestURL());
	    
	    Problema problema = createProblemaBuilder(status, tipoProblema, detail)
	    		.mensagemUsuario(detail)
	    		.build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// trata todas a excecoes não tratadas
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    TipoProblema tipoProblema = TipoProblema.ERRO_DE_SISTEMA;
	    String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

	    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
	    // para você durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
	    
	    Problema problema = createProblemaBuilder(status, tipoProblema, detail)
	    		.mensagemUsuario(detail)
	    		.build();

	    return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	// tratando o BindException - no caso quando passamos um paramentro com informaçoes inconsistentes, essa exception será lançada
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		
		TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimentocorreto e tente novamente";
		
		
		List<Problema.Campo> camposComProblemas = bindingResult.getFieldErrors().stream()
				.map(campoErro -> {
					
				String message = messageSource.getMessage(campoErro, LocaleContextHolder.getLocale());
					
				 return Problema.Campo.builder()
						.nome(campoErro.getField())
						.mensagemUsuario(message)
						.build();
				})
				.collect(Collectors.toList());
		
		Problema problema = createProblemaBuilder(status, tipoProblema, detail)
		    .mensagemUsuario(detail)
		    .campos(camposComProblemas)
		    .build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	private String joinPath(java.util.List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}
	
}
