package com.fatec.scel.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS) // permite definer variaveis nao estaticas no @BeforeALL

class TIAPI_REQ023ConsultarLivroTests {
	String urlBase = "/api/v1/livros";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	LivroRepository repository;
	@Autowired
	IMantemLivro servicoMantemLivro;

	@BeforeAll
	void inicializa() {
		repository.deleteAll();
		Livro umLivro = new Livro("1111", "Teste de Software", "Delamaro");
		repository.save(umLivro);
		umLivro = new Livro("2222", "Engenharia de Software", "Pressman");
		repository.save(umLivro);
		List<Livro> livros = servicoMantemLivro.consultaTodos();
		ArrayList<Livro> lista = new ArrayList<Livro>();
		livros.forEach(cliente -> lista.add(cliente));
		lista.forEach(cli -> System.out.println("clientes nesta sessao =>" + cli.toString()));
	}
	@Test
	void ct01_quando_cosulta_todos_retorna2() {
		// Dado - que existem 2 registros cadastrados
		ParameterizedTypeReference<List<Livro>> tipoRetorno = new ParameterizedTypeReference<List<Livro>>() {};
		// Quando - solicita uma requisicao get consulta todos
		ResponseEntity<List<Livro>> resposta = testRestTemplate.exchange(urlBase, HttpMethod.GET, null, tipoRetorno);
		// Entao - retorna 2
		assertEquals(2, resposta.getBody().size());
		assertEquals("200 OK", resposta.getStatusCode().toString());
		// validacao do estado
		Livro re = new Livro("1111", "Teste de Software", "Delamaro");
		Livro ro = resposta.getBody().get(0);
		re.setId(ro.getId());
		assertTrue(re.equals(ro));
	}

	@Test
	public void ct02_quando_consulta_isbn_valido_retorna_os_detalhes_do_livro() throws Exception {
		// Dado - que o livro esta disponivel para consulta
		String isbn = "1111";
		// Quando - o usuario consulta pelo isbn
		ResponseEntity<Livro> resposta = testRestTemplate.getForEntity(urlBase + "/" + isbn, Livro.class);
		// Entao - retorna os detalhes do livro
		assertEquals("200 OK", resposta.getStatusCode().toString());
		Livro ro = resposta.getBody();
		Livro re = new Livro("1111", "Teste de Software", "Delamaro");
		re.setId(ro.getId()); // id deve ser inicializado no teste 
		assertTrue(re.equals(ro));
		
	}

	@Test
	public void ct03_quando_consulta_isbn_nao_cadastrado_retorna_not_found() throws Exception {
		// Dado - que isbn nao esta cadastrado
		String isbn = "3333";
		// Quando - o usuario consulta isbn nao cadastrado 
		ResponseEntity<Livro> resposta = testRestTemplate.getForEntity(urlBase + isbn, Livro.class);
		// Entao - retorna not found assertFalse(ro.isPresent());
		assertEquals("404 NOT_FOUND", resposta.getStatusCode().toString());
	}

}
