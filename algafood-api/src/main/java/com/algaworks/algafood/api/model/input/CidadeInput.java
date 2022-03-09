package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.model.input.id.EstadoInputId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	private Long id;

	@ApiModelProperty(value = "Manaus")
	private String nome;

	private EstadoInputId estado;

}
