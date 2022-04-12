package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.v1.model.input.id.CidadeInputId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "38400-000", required = true)
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	private String logradouro;

	@ApiModelProperty(example = "\"1500\"", required = true)
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@ApiModelProperty(example = "Centro", required = true)
	private String bairro;

	@Valid
	@NotNull
	private CidadeInputId cidade;
}
