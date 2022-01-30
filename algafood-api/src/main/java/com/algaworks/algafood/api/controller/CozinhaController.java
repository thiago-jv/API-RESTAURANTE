package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
	
//	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
	   Cozinha cozinha = cozinhaRepository.buscar(cozinhaId); 
		
	   HttpHeaders headers = new HttpHeaders();
	   headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas/");
	   
	   //return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	   //return ResponseEntity.ok(cozinha);
	   return  ResponseEntity
			   .status(HttpStatus.FOUND)
			   .headers(headers)
			   .build();
	}
	
}
