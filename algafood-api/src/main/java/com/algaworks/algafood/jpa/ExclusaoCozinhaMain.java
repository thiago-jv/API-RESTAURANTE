package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;

public class ExclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		
	 CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
	 
	 Cozinha cozinha = new Cozinha();
	 cozinha.setId(4L);
	 
	 cadastroCozinha.remover(cozinha.getId());
	
	 List<Cozinha> cozinhas = cadastroCozinha.listar();
	 for (Cozinha c : cozinhas) {
		System.out.println("Id: " +c.getId() + "-" + "Nome: " +c.getNome());
	}
		
	 
	}
}
