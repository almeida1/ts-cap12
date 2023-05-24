package com.fatec.scel.model.matemlivro;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true) //tratado na camada de persistencia 
	private String isbn;
	private String titulo;
	private String autor;
	public Livro() {
	}
	public Livro(String i, String t, String a) {
		setIsbn(i);
		setTitulo(t);
		setAutor(a);
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		if (isbn == null || isbn.isBlank() || isbn.length() > 4 || isbn.length() < 4)
			throw new IllegalArgumentException("O ISBN é formado por 4 caracteres");
		else
			this.isbn = isbn;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		if (titulo == null || titulo.isBlank() )
			throw new IllegalArgumentException("O titulo não deve estar em branco");
		else
			this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		if (autor == null || autor.isBlank())
			throw new IllegalArgumentException("O autor não deve estar em branco");
		else
			this.autor = autor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(autor, isbn, titulo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(isbn, other.isbn)
				&& Objects.equals(titulo, other.titulo);
	}
}