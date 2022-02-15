package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.model.input.id.CidadeInputId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	private String cep;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	@Valid
	@NotNull
	private CidadeInputId cidade;
}
