package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.damain.model.Cozinha;
import com.algaworks.algafood.damain.repository.CozinhaRepository;


@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
	   Cozinha cozinha = cozinhaRepository.buscar(cozinhaId); 	
	   
	   if(cozinha != null) {
		   return ResponseEntity.ok(cozinha);
	   }
	   
	   //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	   return ResponseEntity.notFound().build();
	}
	
}
