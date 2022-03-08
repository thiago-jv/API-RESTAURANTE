package com.algaworks.algafood.domain.service.email;

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	@Getter
	@Setter
	@Builder
	class Mensagem {
		
		@Singular
		private Set<String> destinatarios;
		
		@NotNull
		private String assunto;
		
		@NotNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
		
	}

}
