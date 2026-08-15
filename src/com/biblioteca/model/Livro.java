package com.biblioteca.model;

import java.util.Objects;

public class Livro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private CategoriaLivro categoria;
    private StatusLivro status;
    private int anoPublicacao;

    public Livro(String titulo, String autor, String isbn, CategoriaLivro categoria, int anoPublicacao) {
        this.titulo = Objects.requireNonNull(titulo, "Título não pode ser nulo.");
        this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo.");
        this.isbn = Objects.requireNonNull(isbn, "ISBN não pode ser nulo.");
        this.categoria = categoria != null ? categoria : CategoriaLivro.OUTRO;
        this.anoPublicacao = anoPublicacao;
        this.status = StatusLivro.DISPONIVEL;
    }

    public boolean isDisponivel() {
        return this.status == StatusLivro.DISPONIVEL;
    }

    public void marcarComoEmprestado() {
        if (!isDisponivel()) {
            throw new IllegalStateException("Livro '" + titulo + "' não está disponível para empréstimo.");
        }
        this.status = StatusLivro.EMPRESTADO;
    }

    public void marcarComoDisponivel() {
        this.status = StatusLivro.DISPONIVEL;
    }

    public void marcarComoReservado() {
        if (!isDisponivel()) {
            throw new IllegalStateException("Livro '" + titulo + "' não está disponível para reserva.");
        }
        this.status = StatusLivro.RESERVADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public CategoriaLivro getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaLivro categoria) {
        this.categoria = categoria;
    }

    public StatusLivro getStatus() {
        return status;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%s) | Ano: %d | Status: %s",
                id, titulo, autor, categoria, anoPublicacao, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}