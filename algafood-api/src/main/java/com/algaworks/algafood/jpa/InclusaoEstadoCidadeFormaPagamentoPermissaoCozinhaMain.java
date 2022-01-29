package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.damain.model.Cidade;
import com.algaworks.algafood.damain.model.Estado;
import com.algaworks.algafood.damain.model.FormaPagamento;
import com.algaworks.algafood.damain.model.Permissao;
import com.algaworks.algafood.damain.repository.CidadeRepository;
import com.algaworks.algafood.damain.repository.EstadoRepository;
import com.algaworks.algafood.damain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.damain.repository.PermissaoRepository;

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
		
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cartao de Crédito");
		
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cartao de Débito");
		
		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setDescricao("Dinheiro");
     
		formaPagamentoRepository.salvar(formaPagamento1);
		formaPagamentoRepository.salvar(formaPagamento2);
		formaPagamentoRepository.salvar(formaPagamento3);
		
		Permissao permissao1 = new Permissao();
		permissao1.setNome("CONSULTAR_COZINHAS");
		permissao1.setDescricao("Permite consultar cozinhas");
		
		Permissao permissao2 = new Permissao();
		permissao2.setNome("EDITAR_COZINHAS");
		permissao2.setDescricao("Permite editar cozinhas");
		
		permissaoRepository.salvar(permissao1);
		permissaoRepository.salvar(permissao2);
		
	}
}
