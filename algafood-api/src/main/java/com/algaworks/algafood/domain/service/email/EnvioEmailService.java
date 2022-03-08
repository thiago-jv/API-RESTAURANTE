package com.algaworks.algafood.domain.service.email;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem {
		
		private Set<String> destinatarios;
		private String assunto;
		private String corpo;
		
	}

}
