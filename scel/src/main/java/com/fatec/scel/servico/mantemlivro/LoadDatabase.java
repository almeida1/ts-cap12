package com.fatec.scel.servico.mantemlivro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fatec.scel.model.matemlivro.Livro;
import com.fatec.scel.model.matemlivro.LivroRepository;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	@Bean
	CommandLineRunner initDatabase(LivroRepository repository) {
		return args -> {
			Livro livro = new Livro("aaaa", "Selenium", "Kovalenko" );
			repository.save(livro);
			log.info (">>>>> loaddatabase -> registro incluido");
		};
	}
}
