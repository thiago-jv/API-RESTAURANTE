package com.algaworks.algafood.cozinha;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIntegrationTests {

	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		// habilita os logs para analise
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	/*
	@Test
	public void testarCadastroCozinhaComSucesso() {
		// cenáro
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chineza");
		
		// acão
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();	
	}
	
	@Test()
	public void deveFalharAoCadastrarCozinhaQuandoSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		cadastroCozinhaService.excluir(1L);
    }
    */
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
		  .accept(ContentType.JSON)
		.when()
		  .get()
		.then()
		  .statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		// habilita os logs para analise
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
		   .accept(ContentType.JSON)
		.when()
		  .get()
		.then()
		   .body("nome", hasSize(4))
		   .body("nome", hasItems("Indiana", "Tailandesa"));
	}
	
	@Test
	public void testeRetornandoStatus201_QuandoCadastrarCozinha() {
		given()
		 .body("{ \"nome\": \"Chinesa\" }")
		 .contentType(ContentType.JSON)
		 .accept(ContentType.JSON)
		.when()
		 .post()
		.then()
		 .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		
	}
	
	
}
