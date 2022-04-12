package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Estado, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Getter
@Setter
public class EstadoModel{

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(value = "Amazonas")
	private String nome;
}
