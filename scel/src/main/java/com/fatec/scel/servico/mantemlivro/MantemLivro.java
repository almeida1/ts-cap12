package com.fatec.scel.servico.mantemlivro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;

@Service
public class MantemLivro implements IMantemLivro {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	LivroRepository repository;

	@Override
	public List<Livro> consultaTodos() {
		List<Livro> result = new ArrayList<Livro>();
		repository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public Livro save(@Valid Livro livro) {
		logger.info(">>>>>> servico save - cadastro de livro ");
		return repository.save(livro);

	}

	@Override
	public Livro consultaPorId(Long id) {
		logger.info(">>>>>> servico consulta por id chamado");
		Optional<Livro> livro = repository.findById(id);
		if (livro.isPresent()) {
			return livro.get();
		} else {
			return null;
		}

	}

	@Override
	public Livro consultaPorIsbn(String isbn) {
		logger.info(">>>>>> servico consulta por isbn chamado");
		return repository.findByIsbn(isbn);
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");
		repository.deleteById(id);
	}
}