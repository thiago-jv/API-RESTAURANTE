package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "htttps://algafood.com.br/dados-invalidos", position = 3)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 2)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 6)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 5)
	private String mensagemUsuario;
	
	@ApiModelProperty(example = "2022-02-03-01T18:09:02.70844Z", position = 7)
	private OffsetDateTime dataHora;
	
	@ApiModelProperty(value = "Lista de ojetos ou campos que geraram o erro (opcional)", position = 8)
	private List<Campo> campos;
	
	@ApiModel("CampoProblema")
	@Getter
	@Builder
	public static class Campo {

		@ApiModelProperty(example = "preço")
		private String nome;
		
		@ApiModelProperty(example = "Preço campo obrigatório")
		private String mensagemUsuario;
		
		
	}
}
