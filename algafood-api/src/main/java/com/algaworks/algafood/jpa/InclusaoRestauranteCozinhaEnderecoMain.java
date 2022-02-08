package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.cidade.CidadeRepository;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;

public class InclusaoRestauranteCozinhaEnderecoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

		Cozinha cozinha01 = new Cozinha();
		cozinha01.setNome("Japonesas 1");

		Cozinha cozinha02 = new Cozinha();
		cozinha02.setNome("Americana 2");

		cadastroCozinha.save(cozinha01);
		cadastroCozinha.save(cozinha02);

		List<Cozinha> cozinhas = cadastroCozinha.findAll();
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.toString());
		}

		System.out.println("-------------");

		Cozinha cozinha1 = cadastroCozinha.findById(12L).orElse(null);
		Cozinha cozinha2 = cadastroCozinha.findById(13L).orElse(null);

		Cidade cidade = cidadeRepository.findById(4L).orElse(null);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Monte das Oliveiras");
		endereco1.setCep("69093118");
		endereco1.setComplemento("aguas de manaus");
		endereco1.setLogradouro("proximo caixa de agua");
		endereco1.setNumero("264");
		endereco1.setCidade(cidade);
		
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Thai Gourmet");
		restaurante1.setTaxaFrete(BigDecimal.valueOf(10));
		restaurante1.setCozinha(cozinha1);
		restaurante1.setEndereco(endereco1);
	
		

		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Thai Delivery");
		restaurante2.setTaxaFrete(BigDecimal.valueOf(9.50));
		restaurante2.setCozinha(cozinha2);
		restaurante2.setEndereco(endereco1);

		cadastroRestaurante.save(restaurante1);
		cadastroRestaurante.save(restaurante2);

		List<Restaurante> restaurantes = cadastroRestaurante.findAll();
		for (Restaurante restaurante : restaurantes) {
			System.out.println(restaurante.toString());
		}

	}
}
