package com.fatec.scel.bd;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import com.fatec.scel.model.matemlivro.Livro;

class TU_REQ021CadatrarLivroTests {
	private Validator validator;
	private ValidatorFactory validatorFactory;
	Livro livro;
	@Test
	void ct01_cadastrar_livro_com_sucesso() {
		// Dado – que o atendente tem um livro não cadastrado
		livro = new Livro("3333", "Introdução ao Teste de Software", "Delamaro");
		// Quando – o usuário confirma o cadastro o sistema valida a entrada
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema confirma o cadastro.
		assertTrue(violations.isEmpty());
		assertEquals(0,violations.size());
		

	}
	@Test
	void ct01_cadastrar_livro_com_titulo_invalido() {
		// Dado – que o atendente tem um livro não cadastrado
		livro = new Livro("3333", "", "Delamaro");
		// Quando – o usuário não informa o título do livro o sistema valida a entrada
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema rejeita o cadastro.
		assertFalse(violations.isEmpty());
		assertEquals(1,violations.size());
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());

	}

}
