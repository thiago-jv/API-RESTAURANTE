package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.cidade.CidadeRepository;
import com.algaworks.algafood.domain.repository.cozinha.CozinhaRepository;
import com.algaworks.algafood.domain.repository.estado.EstadoRepository;
import com.algaworks.algafood.domain.repository.formaPagamento.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.permissao.PermissaoRepository;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;

public class InsertGeral {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);
		
		/////////////// ESTADO ///////////
		Estado estado1 = new Estado();
		estado1.setNome("Minas Gerais");

		Estado estado2 = new Estado();
		estado2.setNome("são Paulo");

		Estado estado3 = new Estado();
		estado3.setNome("Minas Gerais");

		estadoRepository.save(estado1);
		estadoRepository.save(estado2);
		estadoRepository.save(estado3);

		Estado e1 = estadoRepository.findById(1L).orElse(null);
		Estado e2 = estadoRepository.findById(2L).orElse(null);
		Estado e3 = estadoRepository.findById(3L).orElse(null);

		/////////////// ESTADO ///////////

		System.out.println("----------\n");

		/////////////// CIDADE ///////////
		Cidade cidade1 = new Cidade();
		cidade1.setNome("Uberlândia");
		cidade1.setEstado(e1);

		Cidade cidade2 = new Cidade();
		cidade2.setNome("Belo Horizonte");
		cidade2.setEstado(e2);

		Cidade cidade3 = new Cidade();
		cidade3.setNome("São Paulo");
		cidade3.setEstado(e3);

		cidadeRepository.save(cidade1);
		cidadeRepository.save(cidade2);
		cidadeRepository.save(cidade3);

		/////////////// CIDADE ///////////

		System.out.println("----------\n");

		/////////////// FORMA DE PAGAMENTO ///////////
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cartao de Crédito");

		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cartao de Débito");

		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setDescricao("Dinheiro");

		formaPagamentoRepository.save(formaPagamento1);
		formaPagamentoRepository.save(formaPagamento2);
		formaPagamentoRepository.save(formaPagamento3);

		/////////////// FORMA DE PAGAMENTO ///////////

		System.out.println("----------\n");

		/////////////// PERMISSAO ///////////

		Permissao permissao1 = new Permissao();
		permissao1.setNome("CONSULTAR_COZINHAS");
		permissao1.setDescricao("Permite consultar cozinhas");

		Permissao permissao2 = new Permissao();
		permissao2.setNome("EDITAR_COZINHAS");
		permissao2.setDescricao("Permite editar cozinhas");

		permissaoRepository.save(permissao1);
		permissaoRepository.save(permissao2);
       
		/////////////// COZINHA ///////////

		Cozinha cozinha01 = new Cozinha();
		cozinha01.setNome("Japonesas");

		Cozinha cozinha02 = new Cozinha();
		cozinha02.setNome("Americana");

		cadastroCozinha.save(cozinha01);
		cadastroCozinha.save(cozinha02);

		System.out.println("-------------\n");

        
		Cozinha cozinha1 = cadastroCozinha.findById(12L).orElse(null);
		Cozinha cozinha2 = cadastroCozinha.findById(13L).orElse(null);

		Cidade cidades1 = cidadeRepository.findById(4L).orElse(null);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Monte das Oliveiras");
		endereco1.setCep("69093118");
		endereco1.setComplemento("aguas de manaus");
		endereco1.setLogradouro("proximo caixa de agua");
		endereco1.setNumero("264");
		endereco1.setCidade(cidades1);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Thai Gourmet");
		restaurante1.setTaxaFrete(BigDecimal.valueOf(10));
		restaurante1.setDataCadastro(OffsetDateTime.now());
		restaurante1.setCozinha(cozinha1);
		restaurante1.setEndereco(endereco1);
	
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Thai Delivery");
		restaurante2.setTaxaFrete(BigDecimal.valueOf(9.50));
		restaurante2.setDataCadastro(OffsetDateTime.now());
		restaurante2.setCozinha(cozinha2);
		restaurante2.setEndereco(endereco1);

		cadastroRestaurante.save(restaurante1);
		cadastroRestaurante.save(restaurante2);
		

        /////////////// COZINHA ///////////

	}

}
