package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.damain.model.Cozinha;
import com.algaworks.algafood.damain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		
	CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
	 
	 Cozinha cozinha1 = new Cozinha();
	 cozinha1.setNome("Americana");
	 
	 Cozinha cozinha2 = new Cozinha();
	 cozinha2.setNome("Japonesa");
	 
	 
	 cadastroCozinha.adicionar(cozinha1);
	 cadastroCozinha.adicionar(cozinha2);
	 
	 List<Cozinha> cozinhas = cadastroCozinha.listar();
	 for (Cozinha cozinha : cozinhas) {
		System.out.println("Id: " +cozinha.getId() + "-" + "Nome: " +cozinha.getNome());
	}
		
	 
	}
}
