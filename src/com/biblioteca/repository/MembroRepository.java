package com.biblioteca.repository;

import com.biblioteca.model.Membro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MembroRepository {

    private final Map<Long, Membro> membros = new HashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Membro salvar(Membro membro) {
        if (membro.getId() == null) {
            membro.setId(proximoId.getAndIncrement());
        }
        membros.put(membro.getId(), membro);
        return membro;
    }

    public Optional<Membro> buscarPorId(Long id) {
        return Optional.ofNullable(membros.get(id));
    }

    public List<Membro> listarTodos() {
        return new ArrayList<>(membros.values());
    }

    public boolean remover(Long id) {
        return membros.remove(id) != null;
    }
}