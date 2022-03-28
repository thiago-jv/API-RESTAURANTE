package com.algaworks.algafood.api.model.input.id;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputId {

	@ApiModelProperty(example = "1", required = true)
//	@NotNull
	private Long id;
}
