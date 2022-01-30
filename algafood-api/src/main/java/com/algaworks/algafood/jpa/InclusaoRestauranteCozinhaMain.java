package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.damain.model.Cozinha;
import com.algaworks.algafood.damain.model.Restaurante;
import com.algaworks.algafood.damain.repository.CozinhaRepository;
import com.algaworks.algafood.damain.repository.RestauranteRepository;

public class InclusaoRestauranteCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

		Cozinha cozinha01 = new Cozinha();
		cozinha01.setNome("Japonesas");

		Cozinha cozinha02 = new Cozinha();
		cozinha02.setNome("Americana");

		cadastroCozinha.adicionar(cozinha01);
		cadastroCozinha.adicionar(cozinha02);

		List<Cozinha> cozinhas = cadastroCozinha.listar();
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.toString());
		}

		System.out.println("-------------");

		Cozinha cozinha1 = cadastroCozinha.buscar(1L);
		Cozinha cozinha2 = cadastroCozinha.buscar(2L);

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Thai Gourmet");
		restaurante1.setTaxaFrete(BigDecimal.valueOf(10));
		restaurante1.setCozinha(cozinha1);

		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Thai Delivery");
		restaurante2.setTaxaFrete(BigDecimal.valueOf(9.50));
		restaurante2.setCozinha(cozinha2);

		cadastroRestaurante.adicionar(restaurante1);
		cadastroRestaurante.adicionar(restaurante2);

		List<Restaurante> restaurantes = cadastroRestaurante.listar();
		for (Restaurante restaurante : restaurantes) {
			System.out.println(restaurante.toString());
		}

	}
}
