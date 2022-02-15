package com.algaworks.algafood.domain.repository.usuario;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

}
