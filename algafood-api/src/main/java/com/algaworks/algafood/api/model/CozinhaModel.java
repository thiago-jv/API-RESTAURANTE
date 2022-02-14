package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Cozinha, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Getter
@Setter
public class CozinhaModel {

	private Long id;

	private String nome;

}
