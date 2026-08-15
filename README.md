# 📚 Sistema de Gerenciamento de Biblioteca

Projeto feito em Java para praticar Programação Orientada a Objetos (POO). É um sistema simples de biblioteca que roda no console (terminal), sem banco de dados, todos os dados ficam guardados em memória enquanto o programa está rodando.

## 🎯 O que o sistema faz:

- Cadastrar livros
- Cadastrar membros (usuários da biblioteca)
- Listar livros e membros cadastrados
- Emprestar um livro para um membro
- Devolver um livro
- Listar empréstimos em andamento

## 🧠 Regras de negócio:

- Um livro só pode ser emprestado se estiver disponível
- Cada membro tem um limite de 3 livros emprestados ao mesmo tempo
- Não é possível remover um livro que está emprestado no momento
- Todo empréstimo tem um prazo de 14 dias para devolução

## 🛠️ Tecnologias:

- Java (sem frameworks, sem bibliotecas externas)
- Sem banco de dados — os dados são guardados em memória (`HashMap`) e se perdem quando o programa é fechado

## 📁 Estrutura do projeto:

O projeto é dividido em camadas, seguindo boas práticas de POO:

```
src/com/biblioteca/
├── Main.java              -> menu do console, ponto de entrada do programa
├── model/                 -> classes que representam os dados do sistema
│   ├── Livro.java
│   ├── Membro.java
│   ├── Emprestimo.java
│   ├── StatusLivro.java     (enum: DISPONIVEL, EMPRESTADO, RESERVADO)
│   └── CategoriaLivro.java  (enum: FICCAO, TECNICO, CIENCIA, etc.)
├── repository/             -> guarda e busca os dados em memória
│   ├── LivroRepository.java
│   ├── MembroRepository.java
│   └── EmprestimoRepository.java
└── service/                -> regras de negócio do sistema
    └── BibliotecaService.java
```

**Por que separar assim?**
- `model` = o que são as coisas (livro, membro, empréstimo)
- `repository` = onde as coisas ficam guardadas
- `service` = o que pode ou não pode acontecer (regras)
- `Main` = como o usuário interage com tudo isso

Essa separação facilita manutenção: se um dia eu quiser trocar o console por uma interface gráfica, ou adicionar um banco de dados de verdade, só preciso mexer em uma camada por vez, sem quebrar o resto.

## ▶️ Como rodar:

### Pelo IntelliJ (ou outra IDE)
1. Abra o projeto na IDE
2. Rode a classe `Main.java`

### Pelo terminal:
```bash
cd src
javac com/biblioteca/*.java com/biblioteca/model/*.java com/biblioteca/repository/*.java com/biblioteca/service/*.java -d ../out
java -cp ../out com.biblioteca.Main
```

## 🖥️ Exemplo de uso:

```
===== Sistema de Gerenciamento de Biblioteca =====
1 - Cadastrar livro
2 - Cadastrar membro
3 - Listar livros
4 - Listar membros
5 - Emprestar livro
6 - Devolver livro
7 - Listar empréstimos em andamento
0 - Sair
Escolha uma opção:
```

## 🚧 Possíveis melhorias futuras:

- [ ] Impedir cadastro de matrícula/ISBN duplicados
- [ ] Persistir os dados em arquivo ou banco de dados
- [ ] Criar testes automatizados (JUnit)
- [ ] Adicionar reserva de livros
- [ ] Refatorar os repositórios para usar uma interface genérica

## 📌 Sobre:

Projeto feito para estudo de Java e POO (encapsulamento, separação de responsabilidades entre camadas, uso de enums, exceções personalizadas e boas práticas de modelagem de classes).
