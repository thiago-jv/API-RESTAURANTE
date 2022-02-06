package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.cidade.CidadeRepository;
import com.algaworks.algafood.domain.repository.estado.EstadoRepository;
import com.algaworks.algafood.domain.repository.formaPagamento.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.permissao.PermissaoRepository;

public class InclusaoEstadoCidadeFormaPagamentoPermissaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
		
		Estado estado1 = new Estado();
		estado1.setNome("Minas Gerais");
		
		Estado estado2 = new Estado();
		estado2.setNome("são Paulo");
		
		Estado estado3 = new Estado();
		estado3.setNome("Minas Gerais");
		
		estadoRepository.salvar(estado1);
		estadoRepository.salvar(estado2);
		estadoRepository.salvar(estado3);
		
	    Estado e1 = estadoRepository.buscar(1L);
	    Estado e2 = estadoRepository.buscar(2L);
	    Estado e3 = estadoRepository.buscar(3L);
		
	    List<Estado> estados = estadoRepository.listar();
	    
	    for (Estado estado : estados) {
			System.out.println(estado.toString());
		}
	    
	    System.out.println("----------");
	    
		Cidade cidade1 = new Cidade();
		cidade1.setNome("Uberlândia");
		cidade1.setEstado(e1);
		
		Cidade cidade2 = new Cidade();
		cidade2.setNome("Belo Horizonte");
		cidade2.setEstado(e2);
		
		Cidade cidade3 = new Cidade();
		cidade3.setNome("São Paulo");
		cidade3.setEstado(e3);

		cidadeRepository.salvar(cidade1);
		cidadeRepository.salvar(cidade2);
		cidadeRepository.salvar(cidade3);
		
		List<Cidade> cidades = cidadeRepository.listar();
		for (Cidade cidade : cidades) {
			System.out.println(cidade.toString());
		}
		
		System.out.println("----------");
		
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cartao de Crédito");
		
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cartao de Débito");
		
		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setDescricao("Dinheiro");
     
		formaPagamentoRepository.salvar(formaPagamento1);
		formaPagamentoRepository.salvar(formaPagamento2);
		formaPagamentoRepository.salvar(formaPagamento3);
		
		
		List<FormaPagamento> formaPagamentos = formaPagamentoRepository.listar();
		for (FormaPagamento formaPagamento : formaPagamentos) {
			System.out.println(formaPagamento.toString());
		}
		
		System.out.println("----------");
		
		Permissao permissao1 = new Permissao();
		permissao1.setNome("CONSULTAR_COZINHAS");
		permissao1.setDescricao("Permite consultar cozinhas");
		
		Permissao permissao2 = new Permissao();
		permissao2.setNome("EDITAR_COZINHAS");
		permissao2.setDescricao("Permite editar cozinhas");
		
		permissaoRepository.salvar(permissao1);
		permissaoRepository.salvar(permissao2);
		
		List<Permissao> permissaos = permissaoRepository.listar();
		for (Permissao permissao : permissaos) {
			System.out.println(permissao.toString());
		}
	}
}
