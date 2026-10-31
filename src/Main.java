import exception.MatriculaDuplicadaException;
import java.util.List;
import java.util.Scanner;
import model.Aluno;
import model.Desempenho;
import model.RelatorioTurma;
import service.AlunoService;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AlunoService service = new AlunoService();

    public static void main(String[] args) {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();

            try {
                switch (opcao) {
                    case 1 -> cadastrarAluno();
                    case 2 -> listarAlunos();
                    case 3 -> pesquisarAluno();
                    case 4 -> registrarNotas();
                    case 5 -> consultarDesempenho();
                    case 6 -> editarAluno();
                    case 7 -> removerAluno();
                    case 8 -> exibirRelatorio();
                    case 9 -> {
                        System.out.println("Encerrando o StudentManager. Até logo!");
                        executando = false;
                    }
                    default -> erro("Opção inválida. Escolha um número de 1 a 9.");
                }
            } catch (MatriculaDuplicadaException | IllegalArgumentException | IllegalStateException e) {
                erro(e.getMessage());
            }
        }

        scanner.close();
    }

    // ===================== MENU =====================

    private static void exibirMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("       STUDENT MANAGER");
        System.out.println("  SISTEMA DE GERENCIAMENTO ESCOLAR");
        System.out.println("====================================");
        System.out.println();
        System.out.println("[1] Cadastrar aluno");
        System.out.println("[2] Listar alunos");
        System.out.println("[3] Pesquisar aluno");
        System.out.println("[4] Registrar notas");
        System.out.println("[5] Consultar desempenho");
        System.out.println("[6] Editar aluno");
        System.out.println("[7] Remover aluno");
        System.out.println("[8] Relatório geral");
        System.out.println("[9] Sair");
        System.out.println();
    }

    // ===================== FUNCIONALIDADES =====================

    private static void cadastrarAluno() {
        titulo("CADASTRAR ALUNO");
        String nome = lerTexto("Nome completo: ");
        int idade = lerInteiro("Idade: ");
        String matricula = lerTexto("Matrícula: ");
        String curso = lerTexto("Curso/turma: ");

        service.cadastrar(nome, idade, matricula, curso);
        sucesso("Aluno cadastrado com sucesso!");
    }

    private static void listarAlunos() {
        titulo("LISTA DE ALUNOS");
        List<Aluno> lista = service.listarTodos();

        if (lista.isEmpty()) {
            aviso("Nenhum aluno cadastrado.");
            return;
        }
        imprimirTabela(lista);
    }

    private static void pesquisarAluno() {
        titulo("PESQUISAR ALUNO");
        String termo = lerTexto("Digite o nome ou a matrícula: ");

        Aluno porMatricula = service.buscarPorMatricula(termo);
        if (porMatricula != null) {
            imprimirTabela(List.of(porMatricula));
            return;
        }

        List<Aluno> porNome = service.buscarPorNome(termo);
        if (porNome.isEmpty()) {
            aviso("Nenhum aluno encontrado para \"" + termo + "\".");
        } else {
            imprimirTabela(porNome);
        }
    }

    private static void registrarNotas() {
        titulo("REGISTRAR NOTAS");
        String matricula = lerTexto("Matrícula do aluno: ");

        Aluno aluno = service.buscarPorMatricula(matricula);
        if (aluno == null) {
            erro("Aluno não encontrado: " + matricula);
            return;
        }

        System.out.println("Aluno: " + aluno.getNome());

        while (aluno.getNotas().size() < Aluno.MAX_NOTAS) {
            int numero = aluno.getNotas().size() + 1;
            double nota = lerDouble("Nota " + numero + " (0 a 10): ");

            try {
                service.registrarNota(matricula, nota);
                sucesso("Nota " + nota + " registrada.");
            } catch (IllegalArgumentException e) {
                erro(e.getMessage());
                continue; // pergunta a mesma nota de novo
            }

            boolean podeMais = aluno.getNotas().size() < Aluno.MAX_NOTAS;
            if (podeMais && !confirmar("Registrar outra nota?")) {
                break;
            }
        }

        if (aluno.getNotas().size() >= Aluno.MAX_NOTAS) {
            aviso("O aluno já possui o máximo de " + Aluno.MAX_NOTAS + " notas.");
        }
        System.out.println("Notas atuais: " + aluno.getNotas());
    }

    private static void consultarDesempenho() {
        titulo("DESEMPENHO ACADÊMICO");
        String matricula = lerTexto("Matrícula do aluno: ");

        Desempenho d = service.consultarDesempenho(matricula);
        System.out.println();
        System.out.println("Aluno:    " + d.aluno().getNome());
        System.out.println("Notas:    " + d.aluno().getNotas());
        System.out.printf("Média:    %.2f%n", d.media());
        System.out.println("Situação: " + d.situacao().getDescricao());
    }

    private static void editarAluno() {
        titulo("EDITAR ALUNO");
        String matricula = lerTexto("Matrícula do aluno: ");

        Aluno aluno = service.buscarPorMatricula(matricula);
        if (aluno == null) {
            erro("Aluno não encontrado: " + matricula);
            return;
        }

        System.out.println("Editando: " + aluno.getNome() + " (a matrícula não pode ser alterada)");
        String nome = lerTexto("Novo nome [" + aluno.getNome() + "]: ");
        int idade = lerInteiro("Nova idade [" + aluno.getIdade() + "]: ");
        String curso = lerTexto("Novo curso/turma [" + aluno.getCurso() + "]: ");

        service.editar(matricula, nome, idade, curso);
        sucesso("Aluno atualizado com sucesso!");
    }

    private static void removerAluno() {
        titulo("REMOVER ALUNO");
        String matricula = lerTexto("Matrícula do aluno: ");

        Aluno aluno = service.buscarPorMatricula(matricula);
        if (aluno == null) {
            erro("Aluno não encontrado: " + matricula);
            return;
        }

        if (confirmar("Remover " + aluno.getNome() + "?")) {
            service.remover(matricula);
            sucesso("Aluno removido.");
        } else {
            aviso("Remoção cancelada.");
        }
    }

    private static void exibirRelatorio() {
        RelatorioTurma r = service.gerarRelatorio();
        titulo("RELATÓRIO GERAL DA TURMA");
        System.out.println("Total de alunos:      " + r.totalAlunos());
        System.out.println("Alunos avaliados:     " + r.avaliados());
        System.out.println("Aprovados:            " + r.aprovados());
        System.out.println("Em recuperação:       " + r.emRecuperacao());
        System.out.println("Reprovados:           " + r.reprovados());
        System.out.printf("Média geral da turma: %.2f%n", r.mediaGeral());
        if (r.avaliados() < r.totalAlunos()) {
            aviso("Alunos com menos de " + Aluno.MIN_NOTAS + " notas não entram nas médias.");
        }
    }

    // ===================== LEITURA DE DADOS =====================

    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            erro("Este campo não pode ficar vazio.");
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                erro("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    throw new NumberFormatException();
                }
                return valor;
            } catch (NumberFormatException e) {
                erro("Digite um número válido (exemplo: 7.5 ou 7,5).");
            }
        }
    }

    private static boolean confirmar(String pergunta) {
        while (true) {
            System.out.print(pergunta + " (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) {
                return true;
            }
            if (resposta.equals("n")) {
                return false;
            }
            erro("Responda apenas com s ou n.");
        }
    }

    // ===================== EXIBIÇÃO =====================

    private static void imprimirTabela(List<Aluno> lista) {
        System.out.printf("%-12s %-28s %-6s %-16s %s%n",
                "MATRÍCULA", "NOME", "IDADE", "CURSO", "NOTAS");
        System.out.println("-".repeat(80));
        for (Aluno a : lista) {
            System.out.printf("%-12s %-28s %-6d %-16s %s%n",
                    a.getMatricula(), a.getNome(), a.getIdade(), a.getCurso(), a.getNotas());
        }
    }

    private static void titulo(String texto) {
        System.out.println("------------------------------------");
        System.out.println(" " + texto);
        System.out.println("------------------------------------");
    }

    private static void sucesso(String mensagem) {
        System.out.println("[OK] " + mensagem);
    }

    private static void erro(String mensagem) {
        System.out.println("[ERRO] " + mensagem);
    }

    private static void aviso(String mensagem) {
        System.out.println("[!] " + mensagem);
    }
}