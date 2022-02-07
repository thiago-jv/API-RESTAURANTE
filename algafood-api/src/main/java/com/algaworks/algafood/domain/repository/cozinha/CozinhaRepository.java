package com.algaworks.algafood.domain.repository.cozinha;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	List<Cozinha> findTodosByNome(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	List<Cozinha> findByNomeContaining(String nome);
	
}
