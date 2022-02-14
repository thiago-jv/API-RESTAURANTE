package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.model.input.id.EstadoInputId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	private Long id;

	private String nome;

	private EstadoInputId estado;

}
