package com.algaworks.algafood.api.assembler.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.resume.PedidoResumeModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public PedidoResumeModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumeModel.class);
    }
    
    public List<PedidoResumeModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
    
}
