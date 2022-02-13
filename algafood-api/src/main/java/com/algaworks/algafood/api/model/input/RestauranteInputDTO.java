package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.TaxaFrete;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDTO {

	@NotBlank
	private String nome;
	
	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaInputIdDTO cozinha;
}
