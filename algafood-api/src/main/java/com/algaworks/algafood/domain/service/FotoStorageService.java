package com.algaworks.algafood.domain.service;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	@Getter
	@Setter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
	}
}
