package com.biblioteca.repository;

import com.biblioteca.model.Livro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class LivroRepository {

    private final Map<Long, Livro> livros = new HashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(proximoId.getAndIncrement());
        }
        livros.put(livro.getId(), livro);
        return livro;
    }

    public Optional<Livro> buscarPorId(Long id) {
        return Optional.ofNullable(livros.get(id));
    }

    public List<Livro> listarTodos() {
        return new ArrayList<>(livros.values());
    }

    public boolean remover(Long id) {
        return livros.remove(id) != null;
    }

    public List<Livro> buscarPorTitulo(String tituloParcial) {
        String busca = tituloParcial.toLowerCase();
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (livro.getTitulo().toLowerCase().contains(busca)) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
}