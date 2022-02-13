package com.algaworks.algafood.api.controller.restaurante;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhaModelDTO;
import com.algaworks.algafood.api.model.RestauranteModelDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.cozinha.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;
import com.algaworks.algafood.domain.service.restaurante.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@GetMapping
	public List<RestauranteModelDTO> listar() {
		return toCollectionModelDTO(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModelDTO buscar(@PathVariable("restauranteId") Long restauranteId) {
	   Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
	   
	   RestauranteModelDTO restauranteModelDTO = toModelDTO(restaurante);
	   
	   return restauranteModelDTO;     	
	}
	
	@PostMapping
	public RestauranteModelDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranInputDTO) {
		try {
			
			Restaurante restaurante = toDomainObject(restauranInputDTO);
			
			return toModelDTO(cadastroRestauranteService.salvar(restaurante));	
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
			return toModelDTO(cadastroRestauranteService.salvar(restauranteAtual));	
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	
	private RestauranteModelDTO toModelDTO(Restaurante restaurante) {
		RestauranteModelDTO restauranteModelDTO = new RestauranteModelDTO();
		   CozinhaModelDTO cozinhaModelDTO = new CozinhaModelDTO();
		   
		   cozinhaModelDTO.setId(restaurante.getCozinha().getId());
		   cozinhaModelDTO.setNome(restaurante.getCozinha().getNome());
		   
		   restauranteModelDTO.setId(restaurante.getId());
		   restauranteModelDTO.setNome(restaurante.getNome());
		   restauranteModelDTO.setTaxaFrete(restaurante.getTaxaFrete());
		   restauranteModelDTO.setCozinha(cozinhaModelDTO);
		return restauranteModelDTO;
	}

	private List<RestauranteModelDTO> toCollectionModelDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModelDTO(restaurante))
				.collect(Collectors.toList());
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

