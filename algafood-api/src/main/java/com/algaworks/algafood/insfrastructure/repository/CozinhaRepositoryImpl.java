package com.algaworks.algafood.insfrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.damain.model.Cozinha;
import com.algaworks.algafood.damain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cozinha> listar(){
	  TypedQuery<Cozinha> query = entityManager.createQuery("from Cozinha", Cozinha.class);
	  return query.getResultList();
	}
	
	@Transactional
	@Override
	public Cozinha adicionar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}
	
	@Override
	public Cozinha buscar(Long id) {
		return entityManager.find(Cozinha.class, id);
	}
	
	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		entityManager.remove(cozinha);
	}

	
	
	
}
