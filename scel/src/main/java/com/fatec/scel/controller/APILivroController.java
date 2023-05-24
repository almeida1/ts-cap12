package com.fatec.scel.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroDTO;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

@Controller
@RequestMapping("/api/v1/livros")
public class APILivroController {
	Logger logger = LogManager.getLogger(APILivroController.class);
	@Autowired
	IMantemLivro servico; // controller nao conhece a implementacao

	@PostMapping
	// @Operation (summary = "Cadastrar um livro na biblioteca")
	public ResponseEntity<?> create(@RequestBody LivroDTO livroDTO) {

		ResponseEntity<?> response = null;
		logger.info(">>>>>> controller create - post iniciado");
		Optional<Livro> umLivro = Optional.ofNullable(servico.consultarPorIsbn(livroDTO.getIsbn()));
		if (umLivro.isPresent()) {
			logger.info(">>>>>> api livro controller create - livro isbn cadastrado");
			response = ResponseEntity.badRequest().body("Livro já cadastrado");
		} else {
			try {
				Livro livro = livroDTO.retornaUmLivro();
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.cadastrar(livro));
				logger.info(">>>>>> api livro controller create - cadastro realizado com sucesso");
			} catch (IllegalArgumentException e) {
				response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
				logger.info(">>>>>> api livro controller create - dados invalidos");
			}
		}
		return response;
	}

	@GetMapping
	public ResponseEntity<List<Livro>> consultaTodos() {
		logger.info(">>>>>> 1. controller chamou servico consulta todos");
		return ResponseEntity.ok().body(servico.consultarTodos());
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<Livro> findByIsbn(@PathVariable String isbn) {
		logger.info(">>>>>> 1. controller chamou servico consulta por isbn => " + isbn);
		ResponseEntity<Livro> response = null;
		Livro livro = servico.consultarPorIsbn(isbn);
		Optional<Livro> optLivro = Optional.ofNullable(livro);
		if (optLivro.isPresent()) {
			response = ResponseEntity.status(HttpStatus.OK).body(optLivro.get());
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return response;
	}

}
