package com.fatec.scel.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS) // permite definer variaveis nao estaticas no @BeforeALL

class TIAPI_REQ023ConsultarLivroTests {
	String urlBase = "/api/v1/livros/";
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
	public void ct01_quando_consulta_pelo_isbn_entao_retorna_os_detalhes_do_livro() throws Exception {
		// Dado - que existem dois registros no banco de dados
		// Quando - o usuario consulta pelo isbn
		String isbn = "1111";
		ResponseEntity<Livro> resposta = testRestTemplate.getForEntity(urlBase + isbn, Livro.class);
		Livro ro = resposta.getBody();
		// Entao - retorna os detalhes do livro
		Livro re = new Livro("1111", "Teste de Software", "Delamaro");
		re.setId(ro.getId()); // id deve ser inicializado no teste assertTrue(re.equals(ro));
		assertEquals("200 OK", resposta.getStatusCode().toString());
	}

	@Test
	public void ct02_quando_consulta_isbn_nao_cadastrado_retorna_not_found() throws Exception {
		// Dado - que existem dois registros no banco de dados
		// Quando - o usuario consulta isbn nao cadastrado 
		String isbn = "3333";
		ResponseEntity<Livro> resposta = testRestTemplate.getForEntity(urlBase + isbn, Livro.class);
		Optional<Livro> ro = Optional.ofNullable(resposta.getBody());
		// Entao - retorna not found assertFalse(ro.isPresent());
		assertEquals("404 NOT_FOUND", resposta.getStatusCode().toString());
	}

}
