package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.api.v1.model.resume.CidadeResumoModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

	@ApiModelProperty(example = "38400-000")
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;

	@ApiModelProperty(example = "\"1500\"")
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	@ApiModelProperty(example = "Manaus")
	private CidadeResumoModel cidade;
	
}
