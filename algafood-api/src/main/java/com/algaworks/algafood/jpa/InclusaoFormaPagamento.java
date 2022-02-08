package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.formaPagamento.FormaPagamentoRepository;

public class InclusaoFormaPagamento {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
	
		
		FormaPagamentoRepository pagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setDescricao("Cartão de crédito");
		
		FormaPagamento formaPagamento2 = new FormaPagamento();
		formaPagamento2.setDescricao("Cartão de débito");
		
		FormaPagamento formaPagamento3 = new FormaPagamento();
		formaPagamento3.setDescricao("Dinheiro");
		
		pagamentoRepository.save(formaPagamento1);
		pagamentoRepository.save(formaPagamento2);
		pagamentoRepository.save(formaPagamento3);
	}
}
