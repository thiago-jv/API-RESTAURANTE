package com.algaworks.algafood.api.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiaria {

	// trabalhar a solução depois
	private String data;

	private Long totalVendas;

	private BigDecimal totalFaturado;

	public VendaDiaria() {
	}

	public VendaDiaria(Long totalVendas, BigDecimal totalFaturado) {
		this.totalVendas = totalVendas;
		this.totalFaturado = totalFaturado;
	}
	
	public VendaDiaria(String data, Long totalVendas, BigDecimal totalFaturado) {
		this.data = data;
		this.totalVendas = totalVendas;
		this.totalFaturado = totalFaturado;
	}

}
