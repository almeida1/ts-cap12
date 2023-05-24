package com.fatec.scel.bd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fatec.scel.model.matemlivro.Livro;

class TU_REQ021CadatrarLivroTests {

	@Test
	void ct01_cadastrar_livro_com_sucesso() {
		try {
			// Dado – que o atendente tem um livro não cadastrado
			Livro livro;
			// Quando – o usuário confirma o cadastro o sistema valida a entrada
			livro = new Livro("3333", "Introdução ao Teste de Software", "Delamaro");
			// Então – o sistema confirma o cadastro.
			assertNotNull(livro);
		} catch (Exception e) {
			fail("nao deveria gerar exception");
		}
	}

	@Test
	void ct02_cadastrar_livro_com_titulo_invalido() {
		try {
			// Dado – que o atendente tem um livro não cadastrado
			// Quando – o atendente cadastra um livro com informações validas
			Livro livro = new Livro("4444", "", "Delamaro");
			fail("deveria falhar titulo em branco");
		} catch (IllegalArgumentException e) {
			// Então – o sistema verifica os dados E permite a consulta do livro
			assertEquals ("O titulo não deve estar em branco",e.getMessage());
		}
	}

}
