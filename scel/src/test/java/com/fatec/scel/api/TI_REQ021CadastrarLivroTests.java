package com.fatec.scel.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

/*
 * Valida a integração do servico (MantemLivro) com a camada de persistencia
 */
@SpringBootTest
class TI_REQ021CadastrarLivroTests {
	@Autowired
	IMantemLivro mantemLivro;
	@Autowired
	LivroRepository repository;

	@Test
	void ct01_cadastrar_livro_com_sucesso() {
		// Dado – que o atendente tem um livro não cadastrado
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Quando – o atendente cadastra um livro com informações validas
		Livro livroCadastrado = mantemLivro.cadastrar(livro);
		// Então – o sistema verifica os dados E permite a consulta do livro
		assertTrue(livroCadastrado.equals(livro));
	}
}
