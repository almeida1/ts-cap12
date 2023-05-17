package com.fatec.scel.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.servico.mantemlivro.IMantemLivro;

@Controller
@RequestMapping("/api/v1/livros")
public class APILivroController {
	Logger logger = LogManager.getLogger(APILivroController.class);
	@Autowired
	IMantemLivro servico; // controller nao conhece a implementacao
	@PostMapping
	//@Operation (summary = "Cadastrar um livro na biblioteca")
	public ResponseEntity<?> create(@Valid @RequestBody Livro livro, BindingResult result) {
		logger.info(">>>>>> controller create - post iniciado ==>" + result.hasErrors()) ;
		logger.info(">>>>>> controller create - post iniciado ==>" + livro.getTitulo()) ;
		ResponseEntity<?> response = null;
		logger.info(">>>>>> controller create - post iniciado");
		if (result.hasErrors()) {
			logger.info(">>>>>> api livro controller create - entrada de dados inválidos " );
			response = ResponseEntity.badRequest().body("Dados inválidos.");
		} else {
			Optional<Livro> umLivro = Optional.ofNullable(servico.consultaPorIsbn(livro.getIsbn()));
			if (umLivro.isPresent()) {
				logger.info(">>>>>> api livro controller create - livro isbn cadastrado");
				response = ResponseEntity.badRequest().body("Livro já cadastrado");
			} else {
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.save(livro));
				logger.info(">>>>>> api livro controller create - cadastro realizado com sucesso");
			}
		}
		return response;
	}
}
