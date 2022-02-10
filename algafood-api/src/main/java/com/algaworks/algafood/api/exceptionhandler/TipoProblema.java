package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblema {

	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontra", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String titulo;
	
	private String uri;
	
	private TipoProblema(String path, String titulo) {
		this.uri = "https://algafood.com.br" + path;
		this.titulo = titulo;
	}
}
