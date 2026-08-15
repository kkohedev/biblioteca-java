package com.biblioteca.model;

import java.time.LocalDate;
import java.util.Objects;

public class Membro {

    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private LocalDate dataCadastro;
    private int limiteEmprestimos;

    public Membro(String nome, String email, String matricula) {
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo.");
        this.matricula = Objects.requireNonNull(matricula, "Matrícula não pode ser nula.");
        this.dataCadastro = LocalDate.now();
        this.limiteEmprestimos = 3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public void setLimiteEmprestimos(int limiteEmprestimos) {
        this.limiteEmprestimos = limiteEmprestimos;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Matrícula: %s | Email: %s | Limite: %d livros",
                id, nome, matricula, email, limiteEmprestimos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membro)) return false;
        Membro membro = (Membro) o;
        return Objects.equals(id, membro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}