package com.algaworks.algafood.api.v2.assembler.cidade;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssemblerV2 {

	@Autowired
    private ModelMapper modelMapper;
    
    public CidadeModelV2 toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModelV2.class);
    }
    
    public List<CidadeModelV2> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
