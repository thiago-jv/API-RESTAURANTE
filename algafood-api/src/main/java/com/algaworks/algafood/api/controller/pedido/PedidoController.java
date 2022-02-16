package com.algaworks.algafood.api.controller.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.pedido.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoResumeModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.resume.PedidoResumeModel;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.pedido.PedidoRepository;
import com.algaworks.algafood.domain.service.pedido.EmissaoPedidoService;

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

	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

		return pedidoModelAssembler.toModel(pedido);
	}
}
