package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Cidade, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Getter
@Setter
public class CidadeModelV2 {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;

	@ApiModelProperty(value = "Manaus", example = "1")
	private String nome;

//	private EstadoModel estado;
}
