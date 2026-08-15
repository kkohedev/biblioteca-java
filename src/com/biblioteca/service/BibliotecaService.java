package com.biblioteca.service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.model.StatusLivro;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MembroRepository;

import java.util.List;
import java.util.Optional;

public class BibliotecaService {

    private final LivroRepository livroRepository;
    private final MembroRepository membroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public BibliotecaService(LivroRepository livroRepository,
                             MembroRepository membroRepository,
                             EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.membroRepository = membroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.salvar(livro);
    }

    public void removerLivro(Long livroId) {
        Livro livro = buscarLivroOuFalhar(livroId);
        if (livro.getStatus() == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Não é possível remover um livro que está emprestado.");
        }
        livroRepository.remover(livroId);
    }

    public List<Livro> listarLivros() {
        return livroRepository.listarTodos();
    }

    public Membro cadastrarMembro(Membro membro) {
        return membroRepository.salvar(membro);
    }

    public List<Membro> listarMembros() {
        return membroRepository.listarTodos();
    }

    public Emprestimo emprestarLivro(Long livroId, Long membroId) {
        Livro livro = buscarLivroOuFalhar(livroId);
        Membro membro = buscarMembroOuFalhar(membroId);

        long emprestimosAtivos = emprestimoRepository.listarEmAndamento().stream()
                .filter(e -> e.getMembro().getId().equals(membroId))
                .count();

        if (emprestimosAtivos >= membro.getLimiteEmprestimos()) {
            throw new IllegalStateException(
                    "Membro '" + membro.getNome() + "' já atingiu o limite de " +
                            membro.getLimiteEmprestimos() + " empréstimos simultâneos.");
        }

        Emprestimo emprestimo = new Emprestimo(livro, membro);
        return emprestimoRepository.salvar(emprestimo);
    }

    public void devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado: " + emprestimoId));
        emprestimo.registrarDevolucao();
    }

    public List<Emprestimo> listarEmprestimosEmAndamento() {
        return emprestimoRepository.listarEmAndamento();
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.listarTodos();
    }

    private Livro buscarLivroOuFalhar(Long id) {
        return livroRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + id));
    }

    private Membro buscarMembroOuFalhar(Long id) {
        return membroRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Membro não encontrado: " + id));
    }
}