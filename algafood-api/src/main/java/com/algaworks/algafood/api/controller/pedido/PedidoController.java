package com.algaworks.algafood.api.controller.pedido;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.pedido.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoResumeModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.model.resume.PedidoResumeModel;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.repository.pedido.PedidoRepository;
import com.algaworks.algafood.domain.service.pedido.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumeModelAssembler pedidoResumeModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@GetMapping
	public List<PedidoModel> listar() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();

		return pedidoModelAssembler.toCollectionModel(todosPedidos);
	}

	@GetMapping("/resume")
	public List<PedidoResumeModel> listarResume() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();

		return pedidoResumeModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/filterPesquisar")
	public List<PedidoResumeModel> pesquisar(PedidoFilter pedidoFilter) {
		List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter));

		return pedidoResumeModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/pageable")
	public Page<PedidoResumeModel> pesquisarPageable(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

		List<PedidoResumeModel> pedidoResumeModel = pedidoResumeModelAssembler.toCollectionModel(pedidosPage.getContent());
		
		Page<PedidoResumeModel> pedidoResumeModelPage = new PageImpl<>(pedidoResumeModel, pageable, pedidosPage.getTotalElements());
		
		return pedidoResumeModelPage;
	}
	
	@GetMapping("/filter")
	public MappingJacksonValue listarFilter(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoResumeModel> pedidosModel = pedidoResumeModelAssembler.toCollectionModel(pedidos);

		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);		
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if(StringUtils.isNoneBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filterProvider);
				
		return pedidosWrapper;
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = emissaoPedido.emitir(novoPedido);

	        return pedidoModelAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
}
