package com.fatec.scel.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

@SpringBootTest
class TI_REQ02CadastrarLivroTests {
	@Autowired
	IMantemLivro mantemLivro;
	@Autowired
	LivroRepository repository;

	@Test
	void ct01_cadastrar_livro_com_sucesso() {
		// Dado – que o atendente tem um livro não cadastrado
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Quando – o atendente cadastra um livro com informações validas
		Livro livroCadastrado = mantemLivro.save(livro);
		// Então – o sistema verifica os dados E permite a consulta do livro
		assertTrue(livroCadastrado.equals(livro));
	}
}
