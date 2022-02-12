package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {

	private Integer status;
	
	private String type;
	
	private String title;
	
	private String detail;
	
	private String mensagemUsuario;
	
	private OffsetDateTime dataHora;
	
	private List<Campo> campos;
	
	@Getter
	@Builder
	public static class Campo {

		private String nome;
		
		private String mensagemUsuario;
		
		
	}
}
