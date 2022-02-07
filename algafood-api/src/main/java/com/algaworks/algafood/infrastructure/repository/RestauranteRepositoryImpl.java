package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepositoryQueries;

import lombok.var;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
      var jpql = "from Restaurante where nome like :nome "
      		+ "and taxaFrete between :taxaInicial and :taxaFinal";
     
      return entityManager.createQuery(jpql, Restaurante.class)
    		  .setParameter("nome", "%" + nome + "%")
    		  .setParameter("taxaInicial", taxaFreteInicial)
    		  .setParameter("taxaFinal", taxaFreteFinal)
    		  .getResultList();
	}
}
