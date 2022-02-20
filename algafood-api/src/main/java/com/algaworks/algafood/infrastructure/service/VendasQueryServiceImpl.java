package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendasQueryServiceImpl implements VendaQueryService{


	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		// solução o para usar o postgreSQl
		//trabalhar a solução depois
		var functionDateDataCriacao = builder.function("TO_CHAR", String.class,
				root.get("dataCriacao"), 
				builder.literal("yyyy-MM-dd"));
		
		var selection = builder.construct(VendaDiaria.class,
			//	functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
	//	query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
