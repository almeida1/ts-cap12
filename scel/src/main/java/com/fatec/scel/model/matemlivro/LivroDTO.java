package com.fatec.scel.model.matemlivro;

public class LivroDTO {
	private String isbn;
	private String titulo;
	private String autor;
	public LivroDTO(String isbn, String titulo, String autor) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Livro retornaUmLivro(){
	    return new Livro(isbn, titulo, autor);
	}

}
