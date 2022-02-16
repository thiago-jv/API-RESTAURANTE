package com.algaworks.algafood.api.model.resume;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.api.model.UsuarioModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoResumeModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;   
} 