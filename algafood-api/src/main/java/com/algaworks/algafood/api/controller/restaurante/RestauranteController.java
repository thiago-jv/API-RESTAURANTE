package com.algaworks.algafood.api.controller.restaurante;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteModelDTOAssembler;
import com.algaworks.algafood.api.model.RestauranteModelDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.cozinha.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;
import com.algaworks.algafood.domain.service.restaurante.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteModelDTOAssembler restauranteModelAssembler;

	@GetMapping
	public List<RestauranteModelDTO> listar() {
		return restauranteModelAssembler.toCollectionModelDTO(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModelDTO buscar(@PathVariable("restauranteId") Long restauranteId) {
	   Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
	   
	   RestauranteModelDTO restauranteModelDTO = restauranteModelAssembler.toModelDTO(restaurante);
	   
	   return restauranteModelDTO;     	
	}
	
	@PostMapping
	public RestauranteModelDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranInputDTO) {
		try {
			
			Restaurante restaurante = toDomainObject(restauranInputDTO);
			
			return restauranteModelAssembler.toModelDTO(cadastroRestauranteService.salvar(restaurante));	
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModelDTO atualizar(@PathVariable Long restauranteId, @RequestBody RestauranteInputDTO restauranteInputDTO) {
		
		Restaurante restaurante = toDomainObject(restauranteInputDTO);
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		try {
			return restauranteModelAssembler.toModelDTO(cadastroRestauranteService.salvar(restauranteAtual));	
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	
	private Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
		Restaurante restaurante = new Restaurante();
		
		restaurante.setNome(restauranteInputDTO.getNome());
		restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInputDTO.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
		
}

