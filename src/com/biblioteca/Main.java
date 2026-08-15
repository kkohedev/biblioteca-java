package com.biblioteca;

import com.biblioteca.model.CategoriaLivro;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MembroRepository;
import com.biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LivroRepository livroRepository = new LivroRepository();
        MembroRepository membroRepository = new MembroRepository();
        EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
        BibliotecaService service = new BibliotecaService(livroRepository, membroRepository, emprestimoRepository);

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro(service);
                    case 2 -> cadastrarMembro(service);
                    case 3 -> listarLivros(service);
                    case 4 -> listarMembros(service);
                    case 5 -> emprestarLivro(service);
                    case 6 -> devolverLivro(service);
                    case 7 -> listarEmprestimosEmAndamento(service);
                    case 0 -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("Encerrando o sistema. Até logo!");
    }

    private static void exibirMenu() {
        System.out.println("===== Sistema de Gerenciamento de Biblioteca =====");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Cadastrar membro");
        System.out.println("3 - Listar livros");
        System.out.println("4 - Listar membros");
        System.out.println("5 - Emprestar livro");
        System.out.println("6 - Devolver livro");
        System.out.println("7 - Listar empréstimos em andamento");
        System.out.println("0 - Sair");
    }

    private static void cadastrarLivro(BibliotecaService service) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        int ano = lerInteiro("Ano de publicação: ");

        CategoriaLivro categoria = escolherCategoria();

        Livro livro = new Livro(titulo, autor, isbn, categoria, ano);
        service.cadastrarLivro(livro);
        System.out.println("Livro cadastrado com id " + livro.getId());
    }

    private static CategoriaLivro escolherCategoria() {
        CategoriaLivro[] categorias = CategoriaLivro.values();
        System.out.println("Categorias disponíveis:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + " - " + categorias[i]);
        }
        int escolha = lerInteiro("Escolha a categoria: ");
        if (escolha < 1 || escolha > categorias.length) {
            System.out.println("Categoria inválida, usando OUTRO.");
            return CategoriaLivro.OUTRO;
        }
        return categorias[escolha - 1];
    }

    private static void cadastrarMembro(BibliotecaService service) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Membro membro = new Membro(nome, email, matricula);
        service.cadastrarMembro(membro);
        System.out.println("Membro cadastrado com id " + membro.getId());
    }

    private static void listarLivros(BibliotecaService service) {
        List<Livro> livros = service.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        livros.forEach(System.out::println);
    }

    private static void listarMembros(BibliotecaService service) {
        List<Membro> membros = service.listarMembros();
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro cadastrado.");
            return;
        }
        membros.forEach(System.out::println);
    }

    private static void emprestarLivro(BibliotecaService service) {
        Long livroId = lerLong("ID do livro: ");
        Long membroId = lerLong("ID do membro: ");

        Emprestimo emprestimo = service.emprestarLivro(livroId, membroId);
        System.out.println("Empréstimo realizado com sucesso! Id: " + emprestimo.getId() +
                " | Devolução prevista: " + emprestimo.getDataPrevistaDevolucao());
    }

    private static void devolverLivro(BibliotecaService service) {
        Long emprestimoId = lerLong("ID do empréstimo: ");
        service.devolverLivro(emprestimoId);
        System.out.println("Devolução registrada com sucesso.");
    }

    private static void listarEmprestimosEmAndamento(BibliotecaService service) {
        List<Emprestimo> emprestimos = service.listarEmprestimosEmAndamento();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo em andamento.");
            return;
        }
        emprestimos.forEach(System.out::println);
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpa a quebra de linha pendente
        return valor;
    }

    private static Long lerLong(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextLong()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        long valor = scanner.nextLong();
        scanner.nextLine();
        return valor;
    }
}