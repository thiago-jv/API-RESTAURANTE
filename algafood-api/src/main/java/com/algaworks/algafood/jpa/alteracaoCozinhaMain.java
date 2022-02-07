package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;

public class alteracaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		
	 CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
	 
	 Cozinha cozinha1 = new Cozinha();
	 cozinha1.setId(4L);
	 cozinha1.setNome("Japonesas");
	 
	 cadastroCozinha.save(cozinha1);
	 
	 List<Cozinha> cozinhas = cadastroCozinha.findAll();
	 for (Cozinha cozinha : cozinhas) {
		System.out.println("Id: " +cozinha.getId() + "-" + "Nome: " +cozinha.getNome());
	}
	 
	}
}
