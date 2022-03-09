package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.model.input.id.EstadoInputId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	private Long id;

	@ApiModelProperty(value = "Manaus", required = true)
	@NotBlank
	private String nome;

	@Valid
	@NotNull
	private EstadoInputId estado;

}
