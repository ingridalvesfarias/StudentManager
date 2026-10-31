# 🎓 StudentManager

Sistema de gerenciamento escolar em **Java puro**, executado no terminal. Permite cadastrar alunos, registrar notas, calcular médias e acompanhar o desempenho acadêmico de uma turma.

> Projeto desenvolvido como primeiro contato prático com Java, aplicando Programação Orientada a Objetos e boas práticas de organização de código.

## 🎯 Objetivo

Praticar os fundamentos de Java (POO, coleções, tratamento de exceções e entrada de dados) construindo uma aplicação completa, organizada em camadas e com validações, sem frameworks nem banco de dados.

## ✨ Funcionalidades

- Cadastro de alunos com nome, idade, matrícula única e curso/turma
- Listagem de todos os alunos em formato de tabela
- Pesquisa por nome (busca parcial) ou matrícula
- Registro de notas de 0 a 10 (de 2 a 3 notas por aluno)
- Cálculo automático da média e da situação acadêmica
- Edição de dados do aluno (a matrícula não pode ser alterada)
- Remoção de alunos com confirmação
- Relatório geral da turma
- Validação de todas as entradas e prevenção de matrículas duplicadas

### Regra de situação acadêmica

| Média | Situação |
|---|---|
| 7,0 ou mais | Aprovado |
| 5,0 a 6,9 | Recuperação |
| Abaixo de 5,0 | Reprovado |

O relatório geral mostra o total de alunos, aprovados, alunos em recuperação, reprovados e a média geral da turma. Alunos com menos de 2 notas contam no total, mas não entram nas médias.

## 🛠️ Tecnologias utilizadas

- Java 21
- Programação Orientada a Objetos (encapsulamento, enum, record, exceção personalizada)
- `ArrayList` para armazenar os alunos
- `Scanner` para entrada de dados
- Git e GitHub para versionamento

## 📁 Estrutura de pastas

```
StudentManager/
├── src/
│   ├── Main.java                      # Menu interativo e leitura de dados
│   ├── model/
│   │   ├── Aluno.java                 # Entidade aluno
│   │   ├── Desempenho.java            # Média e situação de um aluno
│   │   ├── RelatorioTurma.java        # Dados do relatório geral
│   │   └── SituacaoAcademica.java     # Enum: Aprovado, Recuperação, Reprovado
│   ├── service/
│   │   └── AlunoService.java          # Regras de negócio e lista de alunos
│   ├── util/
│   │   └── CalculadoraMedia.java      # Cálculo de média e situação
│   └── exception/
│       └── MatriculaDuplicadaException.java
├── .gitignore
└── README.md
```

## ▶️ Como executar

**Pré-requisito:** JDK 21 ou superior (`java -version` e `javac -version`).

1. Clone o repositório:

```bash
git clone https://github.com/ingridalvesfarias/StudentManager.git
cd StudentManager
```

2. Compile o projeto.

No PowerShell (Windows):

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
```

No Linux ou macOS:

```bash
javac -d out $(find src -name "*.java")
```

3. Execute:

```bash
java -cp out Main
```

## 💻 Exemplos de utilização

### Menu principal

```
====================================
       STUDENT MANAGER
  SISTEMA DE GERENCIAMENTO ESCOLAR
====================================

[1] Cadastrar aluno
[2] Listar alunos
[3] Pesquisar aluno
[4] Registrar notas
[5] Consultar desempenho
[6] Editar aluno
[7] Remover aluno
[8] Relatório geral
[9] Sair

Escolha uma opção:
```

### Cadastro de aluno

```
Nome completo: Maria Silva
Idade: 20
Matrícula: 2024001
Curso/turma: Informática
[OK] Aluno cadastrado com sucesso!
```

### Consulta de desempenho

```
Aluno:    Maria Silva
Notas:    [8.5, 7.0]
Média:    7,75
Situação: Aprovado
```

### Relatório geral

```
Total de alunos:      4
Alunos avaliados:     3
Aprovados:            1
Em recuperação:       1
Reprovados:           1
Média geral da turma: 5,39
```

## 🧠 Conceitos aplicados

- Encapsulamento com atributos privados, construtores, getters e setters
- Separação de responsabilidades (modelo, serviço, utilitário e interface)
- Tratamento de exceções, incluindo uma exceção personalizada
- Validação de dados de entrada
- `enum` e `record` do Java moderno

## 🚀 Próximos passos

- Persistência dos dados em arquivo ou banco de dados
- Testes unitários com JUnit
- Interface gráfica ou versão web

## 👩‍💻 Autora

**Ingrid Farias** — [GitHub](https://github.com/ingridalvesfarias)