package com.algaworks.algafood.domain.repository.pedido;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
