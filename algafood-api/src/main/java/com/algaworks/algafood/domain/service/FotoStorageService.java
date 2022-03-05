package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FotoStorageService {

	void remover(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" +nomeOriginal; 
	}
	
	@Builder
	@Getter
	@Setter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
	}
}
