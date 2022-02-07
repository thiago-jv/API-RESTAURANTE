package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		
	 CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
	 
	 List<Cozinha> cozinhas = cadastroCozinha.findAll();
	 for (Cozinha cozinha : cozinhas) {
		System.out.println(cozinha.getNome());
	}
		
	 
	}
}
