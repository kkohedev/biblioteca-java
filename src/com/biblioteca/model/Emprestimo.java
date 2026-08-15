package com.biblioteca.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Emprestimo {

    private static final int DIAS_PARA_DEVOLUCAO = 14;

    private Long id;
    private Livro livro;
    private Membro membro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucaoReal;

    public Emprestimo(Livro livro, Membro membro) {
        this.livro = Objects.requireNonNull(livro, "Livro não pode ser nulo");
        this.membro = Objects.requireNonNull(membro, "Membro não pode ser nulo");
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(DIAS_PARA_DEVOLUCAO);
        this.dataDevolucaoReal = null;

        this.livro.marcarComoEmprestado();
    }

    public boolean foiDevolvido() {
        return dataDevolucaoReal != null;
    }

    public boolean estaAtrasado() {
        if (foiDevolvido()) {
            return false;
        }
        return LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    public long getDiasAtraso() {
        LocalDate referencia = foiDevolvido() ? dataDevolucaoReal : LocalDate.now();
        long dias = ChronoUnit.DAYS.between(dataPrevistaDevolucao, referencia);
        return Math.max(dias, 0);
    }

    public void registrarDevolucao() {
        if (foiDevolvido()) {
            throw new IllegalStateException("Este empréstimo já foi devolvido.");
        }
        this.dataDevolucaoReal = LocalDate.now();
        this.livro.marcarComoDisponivel();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public Membro getMembro() {
        return membro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    @Override
    public String toString() {
        String statusDevolucao = foiDevolvido()
                ? "Devolvido em " + dataDevolucaoReal
                : (estaAtrasado() ? "ATRASADO (" + getDiasAtraso() + " dias)" : "Em andamento");

        return String.format("[%d] %s -> %s | Emprestado em: %s | Previsto: %s | %s",
                id, membro.getNome(), livro.getTitulo(), dataEmprestimo, dataPrevistaDevolucao, statusDevolucao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emprestimo)) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}