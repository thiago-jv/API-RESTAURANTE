package com.algaworks.algafood.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;

@Component
public class BonificacaoClientePedidoConfirmado {

	// aqui temos um exemplo caso fosse necessário registar um novo evento para outros fins
	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		System.out.println("Calculando pontos para cliente " + event.getPedido().getCliente());
	}
	
}
