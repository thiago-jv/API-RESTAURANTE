package com.algaworks.algafood.cozinha;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
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
		// habilita os logs para analise
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
		  .basePath("/cozinhas")
		  .port(port)
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
		   .basePath("/cozinhas")
		   .port(port)
		   .accept(ContentType.JSON)
		.when()
		  .get()
		.then()
		   .body("nome", Matchers.hasSize(4))
		   .body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}
}
