package com.biblioteca.repository;

import com.biblioteca.model.Emprestimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class EmprestimoRepository {

    private final Map<Long, Emprestimo> emprestimos = new HashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Emprestimo salvar(Emprestimo emprestimo) {
        if (emprestimo.getId() == null) {
            emprestimo.setId(proximoId.getAndIncrement());
        }
        emprestimos.put(emprestimo.getId(), emprestimo);
        return emprestimo;
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return Optional.ofNullable(emprestimos.get(id));
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos.values());
    }

    public List<Emprestimo> listarEmAndamento() {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos.values()) {
            if (!emprestimo.foiDevolvido()) {
                resultado.add(emprestimo);
            }
        }
        return resultado;
    }
}