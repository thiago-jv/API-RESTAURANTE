package com.algaworks.algafood.api.controller.restaurante;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.restaurante.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.restaurante.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.cidade.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.cozinha.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.restaurante.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;
import com.algaworks.algafood.domain.service.restaurante.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

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
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projecao=resumo")
	public List<RestauranteModel> listarResumido() {
		return listar();
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNome() {
		return listar();
	}
	
	@GetMapping("/filter")
	public MappingJacksonValue listarFilter(@RequestParam(required = false) String projecao) {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		List<RestauranteModel> restauranteModel = restauranteModelAssembler.toCollectionModel(restaurantes);
		
		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteModel);
		
		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
		
		if("apenas-nome".equals(projecao)) {
			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);	
		} else if("completo".equals(projecao)) {
			restaurantesWrapper.setSerializationView(null);
		}
		
		return restaurantesWrapper;
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable("restauranteId") Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		RestauranteModel restauranteModelDTO = restauranteModelAssembler.toModel(restaurante);

		return restauranteModelDTO;
	}

	@PostMapping
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranInputDTO) {
		try {

			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranInputDTO);

			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody RestauranteInput restauranteInput) {
		// Restaurante restaurante =
		// restauranteInputDisassembler.toDomainObject(restauranteInput);

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

		// usando o copyToDomainObject , não preciso mais usar o método abaixo, pois a
		// copia é feita abaixo
		// BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
		// "formasPagamento", "endereco", "dataCadastro", "produtos");

		try {
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradaException e) {
		     throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.inativar(restaurantesIds);	
		} catch (RestauranteNaoEncontradaException e) {
		     throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{restauranteId}/ativo")
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{restauranteId}/inativo")
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativa(restauranteId);
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
	}
}
