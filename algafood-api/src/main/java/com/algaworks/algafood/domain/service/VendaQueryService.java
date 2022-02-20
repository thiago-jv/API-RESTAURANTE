package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.api.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaQueryService {

	@Query("select new com.algaworks.algafood.api.model.dto.VendaDiaria(date(dataCriacao), count(id), sum(valorTotal)) " +
			   " from Pedido group by dataCriacao")
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
	
}
