package com.fatec.scel.servico.mantemlivro;

import java.util.List;

import com.fatec.scel.model.matemlivro.Livro;

public interface IMantemLivro {
	public List<Livro> consultarTodos();
	public Livro cadastrar(Livro livro) ;
	public Livro consultarPorId(Long id);
	public Livro consultarPorIsbn(String isbn);
	public void excluir(Long id) ;
}
