package com.algaworks.algafood.domain.repository.cozinha;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	List<Cozinha> findTodosByNome(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	List<Cozinha> findByNomeContaining(String nome);
	
	boolean existsByNome(String nome);
	
}
