package com.algaworks.algafood.api.model.resume;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResumoModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;  

}