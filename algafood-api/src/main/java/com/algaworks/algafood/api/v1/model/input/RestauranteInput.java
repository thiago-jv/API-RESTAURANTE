package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.v1.model.input.id.CozinhaInputId;
import com.algaworks.algafood.core.validation.TaxaFrete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "12.00", required = true)
	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaInputId cozinha;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
}
