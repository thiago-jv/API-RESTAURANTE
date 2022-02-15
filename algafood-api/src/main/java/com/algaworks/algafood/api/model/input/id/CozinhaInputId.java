package com.algaworks.algafood.api.model.input.id;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputId {

	@NotNull
	private Long id;
}
