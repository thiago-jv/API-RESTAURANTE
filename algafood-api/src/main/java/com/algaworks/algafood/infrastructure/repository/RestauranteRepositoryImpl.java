package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepositoryQueries;

import lombok.var;


@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
	
        var builder = entityManager.getCriteriaBuilder();
		
		var criteria = builder.createQuery(Restaurante.class);
		var root = criteria.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
}	
	
	/*
	 * public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
	  var jpql = new StringBuilder();
      jpql.append("from Restaurante where 0 = 0 ");
      
      var paramentros = new HashMap<String, Object>(); 
 
      if(StringUtils.hasLength(nome)) {
    	  jpql.append("and nome like :nome ");
    	  paramentros.put("nome", "%" + nome + "%");
      }
      
      if(taxaFreteInicial != null) {
    	  jpql.append("and taxaFrete >= :taxaInicial ");
    	  paramentros.put("taxaInicial", taxaFreteInicial);
      }
      
      if(taxaFreteInicial != null) {
    	  jpql.append("and taxaFrete <= :taxaFinal ");
    	  paramentros.put("taxaFinal", taxaFreteFinal);
      }
       
      TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
      
      paramentros.forEach((chave, valor) -> query.setParameter(chave, valor));
      
      return query.getResultList();     
	}
	 * 
	 */
