package com.fatec.scel.servico.mantemlivro;

import java.util.List;

import com.fatec.scel.model.matemlivro.Livro;

public interface IMantemLivro {
	public List<Livro> consultaTodos();
	public Livro save(Livro livro) ;
	public Livro consultaPorId(Long id);
	public Livro consultaPorIsbn(String isbn);
	public void delete(Long id) ;
}
