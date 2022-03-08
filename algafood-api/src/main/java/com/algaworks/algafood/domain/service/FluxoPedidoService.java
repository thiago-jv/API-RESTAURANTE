package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.email.EnvioEmailService;
import com.algaworks.algafood.domain.service.email.EnvioEmailService.Mensagem;
import com.algaworks.algafood.domain.service.pedido.EmissaoPedidoService;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private EnvioEmailService envioEmail;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("O pedido de código " + pedido.getCodigo() + " foi confirmado!")
				.destinatario(pedido.getCliente().getEmail())
				.destinatario("teste@hotmail.com").build();
	
		envioEmail.enviar(mensagem);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.CANCELADO.getDescricao()));
	    }
	    
	    pedido.setStatus(StatusPedido.CANCELADO);
	    pedido.setDataCancelamento(OffsetDateTime.now());
	}

	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.ENTREGUE.getDescricao()));
	    }
	    
	    pedido.setStatus(StatusPedido.ENTREGUE);
	    pedido.setDataEntrega(OffsetDateTime.now());
	}
}
