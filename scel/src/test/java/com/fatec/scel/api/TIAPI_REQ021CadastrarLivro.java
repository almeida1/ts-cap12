package com.fatec.scel.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroDTO;
import com.google.gson.Gson;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TIAPI_REQ021CadastrarLivro {
	String urlBase = "/api/v1/livros";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void ct01_cadastrar_livro_com_sucesso() {
		// Dado – que o atendente tem um livro não cadastrado
		String entity = "{\"isbn\":\"3333\",\"titulo\":\"User Stories\",\"autor\":\"Cohn\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(entity, headers);
		// Quando – o atendente cadastra um livro com informações válidas
		ResponseEntity<Livro> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, Livro.class);
		// Então – o sistema valida os dados e retorna mensagem de livro cadastrado com sucesso
		assertEquals("201 CREATED", resposta.getStatusCode().toString());
		String re = "{\"id\":2,\"isbn\":\"3333\",\"titulo\":\"User Stories\",\"autor\":\"Cohn\"}";
		Gson gson = new Gson();
		Livro resultadoEsperado = gson.fromJson(re, Livro.class);
		assertTrue(resultadoEsperado.equals(resposta.getBody()));
	}
	@Test
	public void ct02_cadastrar_livro_metodo_http_nao_disponivel_retorna_http_405() throws Exception {
		// Dado – que o servico está disponivel e o atendente tem um livro não cadastrado
		Livro livro = new Livro("1111", "Teste de Software", "Delamaro");
		HttpEntity<Livro> httpEntity3 = new HttpEntity<>(livro);
		// Quando o cadastro é realizado para um método não disponivel
		ResponseEntity<String> resposta2 = testRestTemplate.exchange(urlBase, HttpMethod.PUT, httpEntity3,
				String.class);
		// Retorna http 405
		assertEquals("405 METHOD_NOT_ALLOWED", resposta2.getStatusCode().toString());
	}
	@Test
	public void ct03_quando_livro_ja_cadastrado_retorna_400() {
		// Dado - que o livro ja esta cadastrado
		Livro livro = new Livro("4444", "User Stories", "Cohn");
		HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, String.class);
		// Quando - o usuario faz uma requisicao POST para cadastrar livro
		resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, String.class);
		// Entao - retorna HTTP200
		assertEquals("400 BAD_REQUEST", resposta.getStatusCode().toString());
		assertEquals("Livro já cadastrado", resposta.getBody());
	}
	@Test
	void ct04_cadastrar_livro_com_titulo_invalido() {
		//Dado que existe um livro nao cadastrado
		//**************************************************************
		// transforma objeto java em JSon
		//**************************************************************
		LivroDTO livroDTO = new LivroDTO("5555","","Cohm");
		Gson dadosDeEntrada = new Gson();
		//**************************************************************
		// Gera a mensagem http
		//**************************************************************
		String entity = dadosDeEntrada.toJson(livroDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(entity, headers);
		// Quando – o atendente cadastra um livro com informações válidas
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, String.class);
		// Então – o sistema valida os dados e retorna mensagem de livro cadastrado com sucesso
		System.out.println(">>>>>> ct02 titulo invalido => " + resposta.getBody());
		assertEquals("400 BAD_REQUEST", resposta.getStatusCode().toString());
		assertEquals("O titulo não deve estar em branco",  resposta.getBody());
	}
	
	
}
