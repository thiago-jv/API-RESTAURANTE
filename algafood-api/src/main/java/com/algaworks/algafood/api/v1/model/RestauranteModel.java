package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.v1.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * Classe que representa Restaurante, porem retornando apenas a propriedades que queremos,
 *  bem como podemos renomear os nomes dos atributos para retornar como queremos 
 */

@Setter
@Getter
public class RestauranteModel {

	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class})
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;

	@ApiModelProperty(example = "12.00")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	//Caso seja necessario exibir os campos abaixo no recurso /todos, é necessário descomentar o JsonView abaixo
	
	@JsonView({RestauranteView.Resumo.class})
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	@JsonView({RestauranteView.Resumo.class})
	@ApiModelProperty(example = "false")
	private Boolean aberto;
	
	@JsonView({RestauranteView.Resumo.class})
	private EnderecoModel endereco;

	@JsonView({RestauranteView.Resumo.class})
	private CozinhaModel cozinha;

}
