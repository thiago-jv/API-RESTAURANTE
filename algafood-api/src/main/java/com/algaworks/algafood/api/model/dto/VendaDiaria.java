package com.algaworks.algafood.api.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

	//trabalhar na solução depois
	//private String data;
	
	private Long totalVendas;
	
	private BigDecimal totalFaturado;
}
