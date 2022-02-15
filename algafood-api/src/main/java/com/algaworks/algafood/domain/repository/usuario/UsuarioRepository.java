package com.algaworks.algafood.domain.repository.usuario;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
