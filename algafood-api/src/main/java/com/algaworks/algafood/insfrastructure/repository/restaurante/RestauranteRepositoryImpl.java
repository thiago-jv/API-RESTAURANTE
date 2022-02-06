package com.algaworks.algafood.insfrastructure.repository.restaurante;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.restaurante.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> listar() {
		TypedQuery<Restaurante> query = entityManager.createQuery("from Restaurante", Restaurante.class);
		  return query.getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public Restaurante adicionar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}

	@Override
	public void remover(Long id) {
		Restaurante restaurante = buscar(id);
		if(restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		entityManager.remove(restaurante);
		
	}

}
