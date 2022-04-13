package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {

	private Long idCidade;

	@ApiModelProperty(value = "Manaus", required = true)
	@NotBlank
	private String nomeCidade;

//	@Valid
//	@NotNull
//	private EstadoInputId estado;

}
